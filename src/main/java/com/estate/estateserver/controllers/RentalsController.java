package com.estate.estateserver.controllers;

import com.estate.estateserver.models.responses.MessageResponse;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
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
    @GetMapping(value = "/rentals", produces = "application/json")
    public ResponseEntity<RentalListResponse> getAllRentals() {
        return ResponseEntity.ok(rentalServices.getAllRentals());
    }

    @Operation(summary = "Save a new rental or update and existing rental", tags = {"Rentals"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental created or updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @PostMapping(value = "/rentals", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<MessageResponse> postRentalPerId(@RequestHeader("Authorization") String token, @RequestParam("picture") MultipartFile picture, @RequestParam("name") String name, @RequestParam("surface") Double surface, @RequestParam("price") Double price, @RequestParam("description") String description) {
        return ResponseEntity.ok(rentalServices.postRentalPerId(token, picture, name, surface, price, description));
    }


}
