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
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalServices.class);
    private final IRentalRepository rentalRepository;
    private final MinioServices minioServices;
    private final AuthenticationServices authenticationServices;

    @Transactional
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

    @Transactional
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

    @Transactional
    public MessageResponse postRentalPerId(String token, MultipartFile picture, String name, Double surface, Double price, String description) {
        //Init response object
        String message = null;
        String picturePath;
        //Try catch block to handle exceptions
        try {
            //1. Retrieve next id to use
            int id = getNextId();
            //2. Retrieve user
            int ownerId = authenticationServices.getUsernameFromToken(token).getId();
            //2. Store picture and return path
            picturePath = minioServices.uploadImage(picture);

            Rental rental = Rental.builder()
                    .id(id)
                    .ownerId(ownerId)
                    .name(name)
                    .surface(surface)
                    .price(price)
                    .picture(picturePath)
                    .description(description)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
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
                throw new RepositoryException("Error while saving rental", rental.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return MessageResponse.builder().message(message).build();
    }

    @Transactional
    public MessageResponse putRentalPerId(int id, FormRequest formRequest) {
        String message;
        try {
            //1. Retrieve rental by id
            Rental rentalFromDb = findById(id);
            Rental rentalToSave = buildRental(IRentalMapper.INSTANCE.formRequestToRental(formRequest), rentalFromDb);
            //2. Verify if rental exists and is ok to update
            message = verifyRentalAndReturnResponseMessage(rentalFromDb, rentalToSave);
            //3. Persist in database
        } catch (Exception e) {
            message = e.getMessage();
            e.printStackTrace();
        }
        LOGGER.debug("Rental updated : {}", message);
        return MessageResponse.builder().message(message).build();
    }

    private Rental buildRental(Rental rentalFromForm, Rental rentalFromDb) {
        return Rental.builder()
                .id(rentalFromDb.getId())
                .name(rentalFromForm.getName())
                .surface(rentalFromForm.getSurface())
                .price(rentalFromForm.getPrice())
                .description(rentalFromForm.getDescription())
                .picture(rentalFromDb.getPicture())
                .ownerId(rentalFromDb.getOwnerId())
                .createdAt(rentalFromDb.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    private String verifyRentalAndReturnResponseMessage(Rental rentalFromDb, Rental rentalToSave) {
        String message;
        if ((rentalFromDb != null) && rentalToSave.getId() == rentalFromDb.getId()) {
            //3. Persist in database
            rentalToSave.setPicture(rentalFromDb.getPicture());
            rentalToSave.setOwnerId(rentalFromDb.getOwnerId());
            rentalToSave.setCreatedAt(rentalFromDb.getCreatedAt());
            rentalToSave.setUpdatedAt(LocalDateTime.now());
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
    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    @Transactional
    public Rental findById(int id) {
        return rentalRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    private int getNextId() {
        return getAllRentals().getRentals().size() + 1;
    }
}
