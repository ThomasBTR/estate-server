package com.estate.estateserver.services;

import com.estate.estateserver.exceptions.RepositoryException;
import com.estate.estateserver.mappers.IRentalMapper;
import com.estate.estateserver.models.entities.Rental;
import com.estate.estateserver.models.requests.FormRequest;
import com.estate.estateserver.models.responses.MessageResponse;
import com.estate.estateserver.models.responses.RentalListResponse;
import com.estate.estateserver.models.responses.RentalResponse;
import com.estate.estateserver.repositories.IRentalRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalServices.class);
    private final IRentalRepository rentalRepository;

    public RentalListResponse getAllRentals() {
        RentalListResponse getRentalListResponse = new RentalListResponse();
        try {
            //1. Retrieve all rentals
            List<Rental> rentals = findAllRentals();
            //2.Verify if there are rentals and return the list of rentals on a response object
            if (!rentals.isEmpty()) {
                List<RentalResponse> gatheredRentalList = IRentalMapper.INSTANCE.rentalListToRentalResponseList(rentals);
                getRentalListResponse = RentalListResponse.builder().rentals(gatheredRentalList).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug("Rentals gathered : {}", getRentalListResponse);
        return getRentalListResponse;
    }

    public MessageResponse postRental(FormRequest formRequest) {
        //Init response object
        String message = null;
        //Try catch block to handle exceptions
        try {
            Rental rental = Rental.builder()
                    .name(formRequest.getName())
                    .surface(formRequest.getSurface())
                    .price(formRequest.getPrice())
                    //TODO: Fix picture
                    .picture(formRequest.getPicture().toString())
                    .description(formRequest.getDescription())
                    .build();
            //1. Retrieve all rentals
            Rental savedRental = saveRental(rental);
            //2.Verify if there are rentals and return the list of rentals on a response object
            if (savedRental != null) {
                RentalResponse response = IRentalMapper.INSTANCE.rentalToRentalResponse(savedRental);
                message = "Rental created !";
                LOGGER.debug("{}: {}", message, response);
            } else {
                message = "Rentals not created !";
                throw new RepositoryException("Rental list not found", formRequest.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return MessageResponse.builder().message(message).build();
    }

    public RentalResponse getRentalById(int id) {
        RentalResponse rentalResponse = new RentalResponse();
        try {
            //1. Retrieve rental by id
            Rental rentalFromDb = findById(id);
            //2. Verify if rental exists
            if (rentalFromDb != null) {
                //3. Map rental to rental response
                rentalResponse = IRentalMapper.INSTANCE.rentalToRentalResponse(rentalFromDb);
            } else {
                throw new RepositoryException(String.format("rental with id %s not found", id), rentalResponse.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug("Rental gathered : {}", rentalResponse);
        return rentalResponse;
    }

    public MessageResponse putRentalPerId(int id, FormRequest formRequest) {
        String message;
        try {
            //1. Retrieve rental by id
            Rental rentalFromDb = findById(id);
            //TODO: Fix this too
            Rental rentalToSave = IRentalMapper.INSTANCE.FormRequestToRental(formRequest);
            //2. Verify if rental exists and is ok to update
            message = verifyRentalAndReturnResponseMessage(rentalFromDb, rentalToSave);
        } catch (Exception e) {
            message = e.getMessage();
            e.printStackTrace();
        }
        LOGGER.debug("Rental updated : {}", message);
        return MessageResponse.builder().message(message).build();
    }

    private String verifyRentalAndReturnResponseMessage(Rental rentalFromDb, Rental rentalToSave) {
        String message;
        if ((rentalFromDb != null) && rentalToSave.getId() == rentalFromDb.getId()) {
            //3. Persist in database
            rentalToSave = saveRental(rentalToSave);
            if (rentalToSave != null) {
                message = "Rental updated successfully";
            } else {
                message = "Rental not updated";
            }
        } else {
            throw new RepositoryException(String.format("rental with id %s not found", rentalToSave.getId()), rentalToSave.toString());
        }
        return message;
    }


    @Transactional
    List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    @Transactional
    List<Rental> saveAllRentals(List<Rental> rentals) {
        return rentalRepository.saveAll(rentals);
    }

    @Transactional
    Rental findById(int id) {
        return rentalRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }


}
