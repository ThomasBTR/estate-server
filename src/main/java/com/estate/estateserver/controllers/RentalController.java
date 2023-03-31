package com.estate.estateserver.controllers;

import com.estate.estateserver.models.requests.FormRequest;
import com.estate.estateserver.models.responses.MessageResponse;
import com.estate.estateserver.models.responses.RentalListResponse;
import com.estate.estateserver.models.responses.RentalResponse;
import com.estate.estateserver.services.RentalServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@SecurityRequirement(name = "token")
public class RentalController {
    private final RentalServices rentalServices;

    @Operation(summary = "Get rental by id", tags = {"Rental per id"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalListResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable int id) {
        return ResponseEntity.ok(rentalServices.getRentalById(id));
    }

    @Operation(summary = "Update rental by id", tags = {"Rental per id"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalListResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<MessageResponse> putRentalPerId(@PathVariable int id, @ModelAttribute FormRequest formRequest) {
        return ResponseEntity.ok(rentalServices.putRentalPerId(id, formRequest));
    }
}
