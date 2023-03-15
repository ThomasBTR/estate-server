package com.estate.estateserver.services;


import com.estate.estateserver.configurations.security.JwtService;
import com.estate.estateserver.models.entities.Role;
import com.estate.estateserver.models.entities.TokenEntity;
import com.estate.estateserver.models.entities.TokenType;
import com.estate.estateserver.models.entities.User;
import com.estate.estateserver.models.requests.AuthenticationRequest;
import com.estate.estateserver.models.requests.RegisterRequest;
import com.estate.estateserver.models.responses.AuthenticationResponse;
import com.estate.estateserver.models.responses.UserResponse;
import com.estate.estateserver.repositories.ITokenRepository;
import com.estate.estateserver.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServices.class);
    private final IUserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ITokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        User savedUser = getSavedUser(user);
        String token = jwtService.generateToken(savedUser);
        saveUserToken(savedUser, token);
        LOGGER.debug("User registered: {}", savedUser.getEmail());
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = getOrElseThrowUser(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        LOGGER.debug("User logged successfully: {}", user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public UserResponse me(String token) {
        String email = getUsernameFromToken(token).getEmail();
        User user = getOrElseThrowUser(email);
        LOGGER.debug("User gathered successfully: {}", user.getEmail());
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Transactional
    User getOrElseThrowUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    @Transactional
    User getUsernameFromToken(String token) {
        return userRepository.findByEmail(jwtService.extractUsername(token.substring(7)))
                .orElseThrow();
    }

    @Transactional
    void saveUserToken(User user, String jwtToken) {
        var token = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = getAllValidTokenByUser(user);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(tokenEntity -> {
            tokenEntity.setExpired(true);
            tokenEntity.setRevoked(true);
        });
        saveUserTokens(validUserTokens);
    }

    @Transactional
    User getSavedUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    void saveUserTokens(List<TokenEntity> validUserTokens) {
        tokenRepository.saveAll(validUserTokens);
    }

    @Transactional
    List<TokenEntity> getAllValidTokenByUser(User user) {
        return tokenRepository.findAllValidTokenByUser(user.getId());
    }
}
