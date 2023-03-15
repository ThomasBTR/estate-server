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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Rentals", description = "The Rental API")
@SecurityRequirement(name = "token")
public class RentalsController {

    private final RentalServices rentalServices;


    @Operation(summary = "Find all rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalListResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @GetMapping(value = "/rentals", produces = "application/json")
    public ResponseEntity<RentalListResponse> getRentals() {
        return ResponseEntity.ok(rentalServices.getAllRentals());
    }

    @Operation(summary = "save a rental list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals saved and returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalListResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @PostMapping(value = "/rentals", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RentalListResponse> postRentals(@RequestBody RentalListRequest rentalListRequest) {
        return ResponseEntity.ok(rentalServices.postRentals(rentalListRequest));
    }

    @Operation(summary = "Find rental by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalListResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @GetMapping(value = "/rentals/{id}", produces = "application/json")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable int id) {
        return ResponseEntity.ok(rentalServices.getRentalById(id));
    }


}
