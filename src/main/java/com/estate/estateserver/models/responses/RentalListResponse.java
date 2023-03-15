package com.estate.estateserver.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalListResponse {

    @Schema(type = "array", description = "rental list")
    List<RentalResponse> rentals;

}
