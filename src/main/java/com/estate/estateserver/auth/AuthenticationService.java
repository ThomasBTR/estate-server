package com.estate.estateserver.auth;

import com.estate.estateserver.mapper.IUserMapper;
import com.estate.estateserver.models.TokenEntity;
import com.estate.estateserver.models.TokenType;
import com.estate.estateserver.security.configuration.JwtService;
import com.estate.estateserver.models.Role;
import com.estate.estateserver.models.User;
import com.estate.estateserver.repositories.ITokenRepository;
import com.estate.estateserver.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository repository;

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
                .build();
        User savedUser= repository.save(user);
        String token = jwtService.generateToken(user);
        saveUserToken(savedUser, token);
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
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public UserResponse me(String token) {
        User verifiedUser = verifyToken(token);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        verifiedUser.getEmail(),
                        verifiedUser.getPassword()
                )
        );
        User user = repository.findByEmail(verifiedUser.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return IUserMapper.INSTANCE.toUserResponse(user);
    }

    private User verifyToken(String token) {
        String username = jwtService.extractUsername(token);
        User user = repository.findByEmail(username)
                .orElseThrow();
        List<TokenEntity> tokenEntities = tokenRepository.findAllValidTokenByUser(user.getId());
        if (tokenEntities.size() != 1 || !tokenEntities.get(0).getToken().equals(token)) {
            throw new RuntimeException("Token not found");
        }
        return user;
    }

    private void saveUserToken(User user, String jwtToken) {
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
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(tokenEntity -> {
            tokenEntity.setExpired(true);
            tokenEntity.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
