package vvikobra.miit.skirental.controllers;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class RootController {

    @GetMapping
    public RepresentationModel<?> getRoot() {
        RepresentationModel<?> rootModel = new RepresentationModel<>();

        rootModel.add(
                linkTo(methodOn(EquipmentController.class)
                        .searchEquipment(null, 0, 10))
                        .withRel("equipment"),

                linkTo(methodOn(BookingController.class)
                        .createBooking(null))
                        .withRel("create-booking"),

                linkTo(methodOn(BookingController.class)
                        .getBooking(null))
                        .withRel("booking"),

                linkTo(methodOn(RootController.class)
                        .getRoot())
                        .slash("swagger-ui.html")
                        .withRel("documentation")
        );

        return rootModel;
    }
}