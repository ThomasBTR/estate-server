package com.estate.estateserver.mappers;

import com.estate.estateserver.models.entities.Rental;
import com.estate.estateserver.models.requests.FormRequest;
import com.estate.estateserver.models.responses.RentalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IRentalMapper {

    IRentalMapper INSTANCE = Mappers.getMapper(IRentalMapper.class);

    RentalResponse rentalToRentalResponse(Rental rental);

    Rental rentalResponseToRental(RentalResponse rental);

    List<RentalResponse> rentalListToRentalResponseList(List<Rental> rentals);

    List<Rental> rentalListRequestToRentalResponseList(List<RentalResponse> rentalListRequest);


    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Rental FormRequestToRental(FormRequest formRequest);

}
