package com.estate.estateserver.controllers;

import com.estate.estateserver.models.responses.AuthenticationResponse;
import com.estate.estateserver.models.responses.RentalListResponse;
import com.estate.estateserver.services.RentalServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "token")
public class RentalsController {

    private final RentalServices rentalServices;


    @Operation(summary = "Find all rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalListResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content) })
    @GetMapping(value = "/rentals", produces = "application/json")
    public ResponseEntity<RentalListResponse> getRentals() {
        return ResponseEntity.ok(rentalServices.getAllRentals());
    }

}
