package com.estate.estateserver.controllers;

import com.estate.estateserver.models.requests.RentalListRequest;
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
public class RentalsController {

    private final RentalServices rentalServices;


    @Operation(summary = "Find all rentals", tags = {"Rentals"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalListResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<RentalListResponse> getRentals() {
        return ResponseEntity.ok(rentalServices.getAllRentals());
    }

    @Operation(summary = "Save a rental list", tags = {"Rentals"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals saved and returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalListResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RentalListResponse> postRentals(@RequestBody RentalListRequest rentalListRequest) {
        return ResponseEntity.ok(rentalServices.postRentals(rentalListRequest));
    }

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


}
