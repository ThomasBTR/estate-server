package com.estate.estateserver.services;

import com.estate.estateserver.mappers.IUserMapper;
import com.estate.estateserver.models.entities.User;
import com.estate.estateserver.models.responses.UserResponse;
import com.estate.estateserver.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServices.class);

    private final IUserRepository userRepository;


    public UserResponse getUserById(int id) {
        UserResponse userResponse = new UserResponse();
        try {
            User user = getUserByIdFromRepository(id);
            if (user != null) {
                userResponse = IUserMapper.INSTANCE.userToUserResponse(user);
            } else {
                String message = String.format("%s %s", "User not found : ", id);
                LOGGER.error(message);
                throw new NotFoundException(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userResponse;
    }

    @Transactional
    User getUserByIdFromRepository(int id) {
        return userRepository.findById(id).orElseThrow();
    }
}
