package com.estate.estateserver.controllers;

import com.estate.estateserver.models.responses.AuthenticationResponse;
import com.estate.estateserver.models.responses.UserResponse;
import com.estate.estateserver.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "token")
public class UsersController {

    private final UserServices service;

    @Operation(summary = "Get user info from token", security = {@SecurityRequirement(name = "token")}, tags = {"User Information"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "retrieved user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> me(@PathVariable() int id) {
        return ResponseEntity.ok(service.getUserById(id));
    }
}
