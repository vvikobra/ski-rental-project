package vvikobra.miit.skirental.assemblers;

import com.example.skirentalcontracts.api.dto.skipass.SkipassResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import vvikobra.miit.skirental.controllers.SkipassController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SkipassModelAssembler implements RepresentationModelAssembler<SkipassResponse, EntityModel<SkipassResponse>> {
    @Override
    public EntityModel<SkipassResponse> toModel(SkipassResponse skipass) {
        return EntityModel.of(skipass,
                linkTo(methodOn(SkipassController.class).getSkipass(skipass.getId())).withSelfRel(),
                linkTo(methodOn(SkipassController.class).useSkipass(skipass.getId())).withRel("use"));
    }
}
