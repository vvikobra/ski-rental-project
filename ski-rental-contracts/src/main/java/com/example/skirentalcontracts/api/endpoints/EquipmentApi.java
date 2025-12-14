package com.example.skirentalcontracts.api.endpoints;

import com.example.skirentalcontracts.api.dto.equipment.EquipmentRequest;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@Tag(name = "equipment", description = "API для работы с экипировкой")
public interface EquipmentApi {

    @Operation(summary = "Получить список доступной экипировки с фильтрацией и пагинацией")
    @ApiResponse(responseCode = "200", description = "Список экипировки")
    @PostMapping("/api/equipment/search")
    PagedModel<EntityModel<EquipmentResponse>> searchEquipment(
            @Valid @RequestBody EquipmentRequest request,
            @Parameter(description = "Номер страницы (0..N)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "10") int size
    );
}