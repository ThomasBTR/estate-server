package com.estate.estateserver.models.responses;

import com.estate.estateserver.models.entities.Rental;
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

    List<Rental> rentals;

}
