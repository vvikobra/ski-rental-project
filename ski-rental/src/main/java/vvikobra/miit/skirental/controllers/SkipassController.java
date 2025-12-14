package vvikobra.miit.skirental.controllers;

import com.example.skirentalcontracts.api.dto.skipass.SkipassResponse;
import com.example.skirentalcontracts.api.endpoints.SkipassApi;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RestController;
import vvikobra.miit.skirental.assemblers.SkipassModelAssembler;
import vvikobra.miit.skirental.services.SkipassService;

import java.util.UUID;

@RestController
public class SkipassController implements SkipassApi {

    private final SkipassService skipassService;
    private final SkipassModelAssembler skipassModelAssembler;

    public SkipassController(SkipassService skipassService, SkipassModelAssembler skipassModelAssembler) {
        this.skipassService = skipassService;
        this.skipassModelAssembler = skipassModelAssembler;
    }

    @Override
    public EntityModel<SkipassResponse> getSkipass(UUID id) {
        return skipassModelAssembler.toModel(skipassService.getSkipass(id));
    }

    @Override
    public EntityModel<SkipassResponse> useSkipass(UUID id) {
        return skipassModelAssembler.toModel(skipassService.useLift(id));
    }
}