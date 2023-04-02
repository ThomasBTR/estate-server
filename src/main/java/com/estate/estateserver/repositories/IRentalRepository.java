package com.estate.estateserver.repositories;

import com.estate.estateserver.models.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRentalRepository extends JpaRepository<Rental, Integer> {
}
