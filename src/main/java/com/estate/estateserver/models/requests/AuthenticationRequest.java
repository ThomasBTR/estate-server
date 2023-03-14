package com.estate.estateserver.models.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @Schema(type = "string", defaultValue = "test@test.com", description = "user email")
    private String email;
    @Schema(type = "string", example = "test!31", description = "user password")
    String password;
}
