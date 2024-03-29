package com.estate.estateserver.controllers;

import com.estate.estateserver.models.requests.AuthenticationRequest;
import com.estate.estateserver.models.requests.RegisterRequest;
import com.estate.estateserver.models.responses.AuthenticationResponse;
import com.estate.estateserver.models.responses.UserResponse;
import com.estate.estateserver.services.AuthenticationServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServices service;

    @Operation(summary = "Get user info from token", security = {@SecurityRequirement(name = "token")}, tags = {"Authentication", "User Information"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.me(token));
    }

    @Operation(summary = "Register a new user", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login request body",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = RegisterRequest.class
                            )
                    )
            )
            RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @Operation(summary = "Login with an existing user", tags = {"Authentication"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content)})
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login request body",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = AuthenticationRequest.class
                            )
                    )
            )
            AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }

}
