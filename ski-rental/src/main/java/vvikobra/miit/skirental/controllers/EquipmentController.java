package vvikobra.miit.skirental.controllers;

import com.example.skirentalcontracts.api.dto.PagedResponse;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentRequest;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentResponse;
import com.example.skirentalcontracts.api.endpoints.EquipmentApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vvikobra.miit.skirental.assemblers.EquipmentModelAssembler;
import vvikobra.miit.skirental.services.EquipmentService;

@RestController
public class EquipmentController implements EquipmentApi {

    private final EquipmentService equipmentService;
    private final EquipmentModelAssembler equipmentAssembler;
    private final PagedResourcesAssembler<EquipmentResponse> pagedResourcesAssembler;

    @Autowired
    public EquipmentController(
            EquipmentService equipmentService,
            EquipmentModelAssembler equipmentAssembler,
            PagedResourcesAssembler<EquipmentResponse> pagedResourcesAssembler
    ) {
        this.equipmentService = equipmentService;
        this.equipmentAssembler = equipmentAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public PagedModel<EntityModel<EquipmentResponse>> searchEquipment(
            @Valid @RequestBody EquipmentRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PagedResponse<EquipmentResponse> equipmentList = equipmentService.getAvailableEquipment(request, page, size);

        Page<EquipmentResponse> equipmentPage = new PageImpl<>(
                equipmentList.content(),
                PageRequest.of(page, size),
                equipmentList.totalElements()
        );

        return pagedResourcesAssembler.toModel(equipmentPage, equipmentAssembler);
    }
}