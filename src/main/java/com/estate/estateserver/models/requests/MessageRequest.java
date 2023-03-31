package com.estate.estateserver.models.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageRequest {
    @Schema(type = "integer", example = "1", description = "user id")
    @NotNull
    private Integer userId;
    @NotBlank
    @Schema(type = "string", example = "Hello, I'm interested in your offer", description = "message")
    private String message;
    @NotNull
    @Schema(type = "integer", example = "1", description = "rental id")
    private Integer rentalId;

}
