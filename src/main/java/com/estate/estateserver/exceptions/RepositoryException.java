package com.estate.estateserver.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webjars.NotFoundException;

public class RepositoryException extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryException.class);

    public RepositoryException(String message, String... args) {
        for (String arg : args) {
            message = String.format("%s , {%s}", message, arg);
        }
        throw new RepositoryException(message);
    }

    public RepositoryException(String message) {
        LOGGER.error("RepositoryException : {}", message);
        throw new NotFoundException(String.format("%s : %s", "Not found : ", message));
    }
}
