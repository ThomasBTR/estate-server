package com.estate.estateserver.services;

import com.estate.estateserver.models.entities.Rental;
import com.estate.estateserver.models.responses.RentalListResponse;
import com.estate.estateserver.repositories.IRentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor

public class RentalServices {

    private final IRentalRepository rentalRepository;


    public RentalListResponse getAllRentals() {
        List<Rental> rentals;
        RentalListResponse rentalListResponse = new RentalListResponse();
        try {
            //1. Retrieve all rentals
            rentals = findAllRentals();
            //2.Verify if there are rentals and return the list of rentals on a response object
            if (!rentals.isEmpty()) {
                rentalListResponse = RentalListResponse.builder().rentals(rentals).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rentalListResponse;
    }

    public RentalListResponse postRentals(List<Rental> rentals) {
        RentalListResponse postRentalResponse = new RentalListResponse();
        try {
            //1. Retrieve all rentals
            rentals = saveAllRentals(rentals);
            //2.Verify if there are rentals and return the list of rentals on a response object
            if (!rentals.isEmpty()) {
                postRentalResponse = RentalListResponse.builder().rentals(rentals).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postRentalResponse;
    }

    @Transactional
    List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    @Transactional
    List<Rental> saveAllRentals(List<Rental> rentals) {
        return rentalRepository.saveAll(rentals);
    }
}
