package com.estate.estateserver.exceptions;

import org.webjars.NotFoundException;

public class RepositoryException extends RuntimeException {
    public RepositoryException(String message, String... args) {
        for (String arg : args) {
            message = String.format("%s , {%s}", message, arg);
        }
        throw new RepositoryException(message);
    }

    public RepositoryException(String message) {
        throw new NotFoundException(String.format("%s : %s", "Not found : ", message));
    }
}
