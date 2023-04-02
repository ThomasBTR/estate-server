package com.estate.estateserver.repositories;

import com.estate.estateserver.models.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Integer> {
}
