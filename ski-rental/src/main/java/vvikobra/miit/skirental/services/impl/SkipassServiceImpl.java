package vvikobra.miit.skirental.services.impl;

import com.example.skirentalcontracts.api.dto.skipass.SkipassResponse;
import com.example.skirentalcontracts.api.exception.InvalidSkipassException;
import com.example.skirentalcontracts.api.exception.ResourceNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vvikobra.events.SkipassUsedEvent;
import vvikobra.miit.skirental.config.RabbitMQConfig;
import vvikobra.miit.skirental.models.entities.Skipass;
import vvikobra.miit.skirental.repositories.Impl.SkipassRepositoryImpl;
import vvikobra.miit.skirental.services.SkipassService;
import vvikobra.miit.skirental.util.Mapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SkipassServiceImpl implements SkipassService {

    private final RabbitTemplate rabbitTemplate;

    private SkipassRepositoryImpl skipassRepository;
    private Mapper mapper;

    public SkipassServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setSkipassRepository(SkipassRepositoryImpl skipassRepository) {
        this.skipassRepository = skipassRepository;
    }

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public SkipassResponse getSkipass(UUID id) {
        Skipass skipass = findSkipassOrThrow(id);
        return mapper.mapToSkipassResponse(skipass);
    }

    @Override
    public SkipassResponse useLift(UUID id) {
        Skipass skipass = skipassRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Skipass", id));
        validateSkipassForLift(skipass);

        decrementLiftCount(skipass);

        SkipassUsedEvent skipassUsedEvent = new SkipassUsedEvent(skipass.getId().toString(), LocalDateTime.now());

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SKIPASS_USED, skipassUsedEvent);

        return mapper.mapToSkipassResponse(skipass);
    }

    private Skipass findSkipassOrThrow(UUID id) {
        return skipassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skipass", id));
    }

    private void validateSkipassForLift(Skipass skipass) {
        if (skipass.getTotalLifts() == null) {
            throw new InvalidSkipassException("Skipass type does not support lift-based usage.");
        }
        if (!skipassRepository.isActive(skipass.getId())) {
            throw new InvalidSkipassException("Skipass is not active.");
        }
        if (skipass.getRemainingLifts() <= 0) {
            throw new InvalidSkipassException("No remaining lifts available for this skipass.");
        }
    }

    private void decrementLiftCount(Skipass skipass) {
        skipassRepository.decrementLift(skipass.getId());
        skipass.setRemainingLifts(skipass.getRemainingLifts() - 1);
    }
}
