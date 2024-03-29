package com.estate.estateserver.models.requests;

import com.estate.estateserver.models.responses.RentalResponse;
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
public class RentalListRequest {

    @Schema(type = "array", description = "rental list")
    List<RentalResponse> rentals;

}
