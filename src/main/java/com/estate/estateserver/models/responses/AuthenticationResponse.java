package com.estate.estateserver.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @Schema(type = "string", example = "eyJhbGciOiwhateveritiz29tIiwiaWF0IjoxNjc4ODA2MjU1LCJleHAiOjE2Nzg4OTI2NTV9.1wZUQLjkEkU0Z8r64bF-8ZMD52lMOK8M4IwaYuNwjmo", description = "token")
    private String token;
}
