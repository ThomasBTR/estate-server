package com.estate.estateserver.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Schema(type = "string", example = "Liliane", description = "username")
    @NotBlank
    private String name;

    @Schema(type = "string", example = "lili@green.com", description = "user email")
    @NotBlank
    private String email;

    @Schema(type = "string", example = "lili4U", description = "password")
    @NotBlank
    private String password;
}
