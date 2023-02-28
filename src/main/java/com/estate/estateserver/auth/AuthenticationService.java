package com.estate.estateserver.auth;

import com.estate.estateserver.security.configuration.JwtService;
import com.estate.estateserver.security.models.Role;
import com.estate.estateserver.security.models.User;
import com.estate.estateserver.security.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository repository;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail())
                //TODO: change to custom exception
                .orElseThrow(() -> new IllegalStateException("User not found"));
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .build();
    }
}
