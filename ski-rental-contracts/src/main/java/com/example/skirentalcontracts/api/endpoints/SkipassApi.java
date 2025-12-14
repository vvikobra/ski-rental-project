package com.example.skirentalcontracts.api.endpoints;

import com.example.skirentalcontracts.api.dto.skipass.SkipassResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;


@Tag(name = "skipasses", description = "API для управления скипассами")
public interface SkipassApi {

    @Operation(summary = "Получить статус скипасса")
    @GetMapping("/api/skipasses/{id}")
    EntityModel<SkipassResponse> getSkipass(@PathVariable("id") UUID id);

    @Operation(summary = "Списать подъём (использование скипасса по lifts)")
    @PostMapping("/api/skipasses/{id}/use")
    EntityModel<SkipassResponse> useSkipass(@PathVariable("id") UUID id);
}
