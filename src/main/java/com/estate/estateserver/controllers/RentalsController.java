package com.estate.estateserver.controllers;

import com.estate.estateserver.models.responses.RentalListResponse;
import com.estate.estateserver.services.RentalServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalServices rentalServices;

    @GetMapping("/rentals")
//    TODO: Verify if this is needed to get security available on the swaggger-ui
//    @Operation(security = {@SecurityRequirement(name = "token")})
    public ResponseEntity<RentalListResponse> getRentals() {
        return ResponseEntity.ok(rentalServices.getAllRentals());
    }

}
