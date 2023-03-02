package com.estate.estateserver.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

//    @GetMapping("/me")
//    public ResponseEntity<String> test()
//    {
//        return ResponseEntity.ok();
//    }

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