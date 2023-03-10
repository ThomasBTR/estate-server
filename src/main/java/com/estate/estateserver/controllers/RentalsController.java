package com.estate.estateserver.controllers;

import com.estate.estateserver.models.responses.RentalListResponse;
import com.estate.estateserver.services.RentalServices;
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

    @GetMapping("/rentals")
    public ResponseEntity<RentalListResponse> getRentals() {
        return ResponseEntity.ok(rentalServices.getAllRentals());
    }

}
