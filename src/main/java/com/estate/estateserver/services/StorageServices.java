package com.estate.estateserver.services;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class StorageServices {

    private static final String IMAGES = "src/main/resources/static/images/";

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServices.class);

    public String store(MultipartFile picture) throws FileSystemException {
        Path path = Paths.get(IMAGES + picture.getOriginalFilename());
        try {
            byte[] pictureBytes = picture.getBytes();
            Files.write(path, pictureBytes);
        } catch (IOException e) {
            LOGGER.error("Error while storing picture : {}", path);
            throw new FileSystemException(path.toString(), null, e.getMessage());
        }
        return path.toString();
    }
}
