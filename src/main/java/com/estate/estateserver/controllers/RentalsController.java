package com.estate.estateserver.controllers;

import com.estate.estateserver.models.responses.RentalListResponse;
import com.estate.estateserver.services.RentalServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RentalsController {

    private final RentalServices rentalServices;

    @GetMapping("/rentals")
//    TODO: Uncomment this line to enable security
//    @Operation(security = {@SecurityRequirement(name = "token")})
//    public ResponseEntity<RentalListResponse> getRentals(@RequestHeader("Authorization") String token) {
    public ResponseEntity<RentalListResponse> getRentals(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(rentalServices.getAllRentals());
    }

}
