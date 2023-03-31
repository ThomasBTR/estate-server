package com.estate.estateserver.services;

import com.estate.estateserver.models.entities.Message;
import com.estate.estateserver.models.entities.Rental;
import com.estate.estateserver.models.entities.User;
import com.estate.estateserver.models.requests.MessageRequest;
import com.estate.estateserver.models.responses.MessageResponse;
import com.estate.estateserver.repositories.IMessageRepository;
import com.estate.estateserver.repositories.IRentalRepository;
import com.estate.estateserver.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServices.class);
    private final IMessageRepository messageRepository;
    private final IUserRepository userRepository;
    private final IRentalRepository rentalRepository;

    @Transactional
    public MessageResponse postMessage(MessageRequest messageRequest) {
        MessageResponse messageResponse = null;
        try {
            User verifiedUser = verifieUser(messageRequest);
            Rental verifiedRental = getReferenceById(messageRequest);
            messageResponse = buildAndSaveMessage(messageRequest, verifiedUser, verifiedRental);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messageResponse;
    }

    @Transactional
    public MessageResponse buildAndSaveMessage(MessageRequest messageRequest, User verifiedUser, Rental verifiedRental) {
        if (verifiedUser != null && verifiedRental != null) {
            Message message = Message.builder()
                    .messageContent(messageRequest.getMessage())
                    .rental(verifiedRental)
                    .user(verifiedUser)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            cascadeAndPersistMessage(message, verifiedRental, verifiedUser);
        }
        return MessageResponse.builder()
                .message("Message send with success")
                .build();
    }

    @Transactional
    public void cascadeAndPersistMessage(Message message, Rental rental, User user) {
        try {
            messageRepository.save(message);
            User userPersisted = persistUser(user, message);
            Rental rentalPersisted = persistRental(rental, message);
            message.setUser(userPersisted);
            message.setRental(rentalPersisted);
            LOGGER.info("Message saved successfully : {}", message.getMessageContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public User persistUser(User user, Message message) {
        List<Message> messages = user.getMessagesSent();
        messages.add(message);
        user.setMessagesSent(messages);
        return userRepository.save(user);
    }

    @Transactional
    public Rental persistRental(Rental rental, Message message) {
        List<Message> messages = rental.getMessages();
        messages.add(message);
        rental.setMessages(messages);
        return rentalRepository.save(rental);
    }

    @Transactional
    public Rental getReferenceById(MessageRequest messageRequest) {
        return rentalRepository.getReferenceById(messageRequest.getRentalId());
    }

    @Transactional
    public User verifieUser(MessageRequest messageRequest) {
        return userRepository.getReferenceById(messageRequest.getUserId());
    }
}
