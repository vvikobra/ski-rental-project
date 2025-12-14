package vvikobra.miit.skirental.services.impl;

import com.example.skirentalcontracts.api.dto.PagedResponse;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentRequest;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vvikobra.miit.skirental.repositories.Impl.EquipmentRepositoryImpl;
import vvikobra.miit.skirental.services.EquipmentService;
import vvikobra.miit.skirental.util.Mapper;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private EquipmentRepositoryImpl equipmentRepository;
    private Mapper mapper;

    @Autowired
    public void setEquipmentRepository(EquipmentRepositoryImpl equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PagedResponse<EquipmentResponse> getAvailableEquipment(EquipmentRequest request, int page, int size) {

        List<EquipmentResponse> available = equipmentRepository.findAvailable(
                        request.timeFrom(), request.timeTo()).stream()
                .filter(eq -> request.type() == null || eq.getType().getName().equalsIgnoreCase(request.type()))
                .filter(eq -> request.height() == null
                        || (eq.getMinHeight() != null && eq.getMaxHeight() != null
                        && eq.getMinHeight() <= request.height() && eq.getMaxHeight() >= request.height()))
                .filter(eq -> request.weight() == null
                        || (eq.getMinWeight() != null && eq.getMaxWeight() != null
                        && eq.getMinWeight() <= request.weight() && eq.getMaxWeight() >= request.weight()))
                .filter(eq -> request.skillLevel() == null || eq.getLevel().getName().equalsIgnoreCase(request.skillLevel()))
                .filter(eq -> request.shoeSize() == null
                        || (eq.getShoeSize() != null && eq.getShoeSize().equals(request.shoeSize())))
                .map(mapper::mapToEquipmentResponse)
                .toList();

        int totalElements = available.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalElements);

        List<EquipmentResponse> pageContent = (fromIndex > toIndex) ? List.of() : available.subList(fromIndex, toIndex);

        return new PagedResponse<>(pageContent, page, size, totalElements, totalPages, page >= totalPages - 1);
    }
}
