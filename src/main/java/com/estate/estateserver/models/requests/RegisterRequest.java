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
public class RegisterRequest {

    @Schema(type = "string", example = "Liliane", description = "username")
    private String name;

    @Schema(type = "string", example = "lili@green.com", description = "user email")
    private String email;
    @Schema(type = "string", example = "lili4U", description = "password")
    private String password;
}
