package vvikobra.miit.skirental.assemblers;

import com.example.skirentalcontracts.api.dto.equipment.EquipmentResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import vvikobra.miit.skirental.controllers.EquipmentController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EquipmentModelAssembler implements RepresentationModelAssembler<EquipmentResponse, EntityModel<EquipmentResponse>> {

    @Override
    public EntityModel<EquipmentResponse> toModel(EquipmentResponse equipment) {
        return EntityModel.of(equipment,
                linkTo(methodOn(EquipmentController.class).searchEquipment(null, 0, 10)).withRel("collection")
        );
    }

    @Override
    public CollectionModel<EntityModel<EquipmentResponse>> toCollectionModel(Iterable<? extends EquipmentResponse> equipments) {
        return RepresentationModelAssembler.super.toCollectionModel(equipments)
                .add(linkTo(methodOn(EquipmentController.class).searchEquipment(null, 0, 10)).withSelfRel());
    }
}