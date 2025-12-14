package com.example.skirentalcontracts.api.endpoints;

import com.example.skirentalcontracts.api.dto.StatusResponse;
import com.example.skirentalcontracts.api.dto.booking.BookingRequest;
import com.example.skirentalcontracts.api.dto.booking.BookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "bookings", description = "API для работы с бронированиями")
public interface BookingApi {

    @Operation(summary = "Создать бронирование")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Бронирование создано"),
            @ApiResponse(responseCode = "400", description = "Невалидный запрос",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @PostMapping("/api/bookings")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    EntityModel<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request);

    @Operation(summary = "Получить бронирование по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Бронирование найдено"),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @GetMapping("/api/bookings/{id}")
    EntityModel<BookingResponse> getBooking(@PathVariable("id") UUID id);

    @Operation(summary = "Отменить бронирование")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Бронирование отменено"),
            @ApiResponse(responseCode = "404", description = "Бронирование не найдено",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class))),
            @ApiResponse(responseCode = "409", description = "Невозможно отменить данное бронирование",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @PutMapping("/api/bookings/{id}/cancel")
    EntityModel<BookingResponse> cancelBooking(@PathVariable("id") UUID id);
}