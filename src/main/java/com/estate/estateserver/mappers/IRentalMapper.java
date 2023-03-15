package com.estate.estateserver.mappers;

import com.estate.estateserver.models.entities.Rental;
import com.estate.estateserver.models.responses.RentalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IRentalMapper {

    IRentalMapper INSTANCE = Mappers.getMapper(IRentalMapper.class);

    RentalResponse rentalToRentalResponse(Rental rental);

    Rental rentalResponseToRental(RentalResponse rental);

    List<RentalResponse> rentalListToRentalResponseList(List<Rental> rentals);

    List<Rental> rentalListRequestToRentalResponseList(List<RentalResponse> rentalListRequest);

}
