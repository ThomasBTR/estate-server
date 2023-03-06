package com.estate.estateserver.controllers;

import com.estate.estateserver.models.requests.AuthenticationRequest;
import com.estate.estateserver.models.responses.AuthenticationResponse;
import com.estate.estateserver.models.requests.RegisterRequest;
import com.estate.estateserver.models.responses.UserResponse;
import com.estate.estateserver.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@RequestHeader("Authorization") String token)
    {
        return ResponseEntity.ok(service.me(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
             @RequestBody RegisterRequest request
    )
    {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    )
    {
        return ResponseEntity.ok(service.login(request));
    }

}
