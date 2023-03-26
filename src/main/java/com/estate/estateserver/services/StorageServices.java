package com.estate.estateserver.services;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class StorageServices {


    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServices.class);

    private final Path fileStorageLocation;

    @Autowired
    public StorageServices(Environment env) {
        this.fileStorageLocation = Paths.get(env.getProperty("estate.file.upload-dir", "C:\\Users\\User\\Desktop\\images\\"))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            LOGGER.error("Could not create the directory where the uploaded files will be stored.", ex);
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName =
                new Date().getTime() + "-file." + getFileExtension(file.getOriginalFilename());

        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation.toAbsolutePath().toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
