package com.estate.estateserver.models.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {

    @Schema(type = "int", example = "1", description = "user id")
    private int id;

    @Schema(type = "string", example = "test TEST", description = "username")
    private String name;
    @Schema(type = "string", example = "test@test.com", description = "email")
    private String email;

    @Schema(type = "Date", example = "2012-12-02T00:00:00", description = "user creation date")
    private LocalDateTime createdAt;

    @Schema(type = "Date", example = "2012-12-02T00:00:00", description = "user last update date")
    private LocalDateTime updatedAt;
}
