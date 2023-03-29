package com.estate.estateserver.services;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioServices.class);
    MinioClient minioClient;

    @Value(value = "${estate.minio.endpoint:http://localhost}")
    private String endpoint;

    @Value(value = "${estate.minio.port:9000}")
    private int port;

    @Value(value = "${estate.minio.user:estate-admin}")
    private String user;

    @Value(value = "${estate.minio.password:estate-minio-secret-key}")
    private String password;

    @Value(value = "${estate.minio.bucket:estate-images}")
    private String bucketName;

    @Value(value = "${estate.minio.retentionHours:24}")
    private int retentionHours;


    public String uploadImage(MultipartFile file) {
        minioClient = new MinioClient.Builder()
                .endpoint(endpoint, port, false)
                .credentials(user, password)
                .build();
        // return url of the uploaded image
        String url = null;
        try {
            // Check whether the bucket already exists.
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                LOGGER.error("Bucket already exists.");
            } else {
                // Make a new bucket called estate-images to hold photos.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            String originalFileName = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFileName);

            // Upload the zip file to the bucket with putObject
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(originalFileName).stream(file.getInputStream(), file.getSize(), -1).build());
            url = getImageUrl(originalFileName);
            LOGGER.info("{}.{} is successfully uploaded as object to bucket {}", originalFileName, fileExtension, bucketName);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            LOGGER.error("Error occurred: {}", e.getMessage(), e);
        }
        return url;
    }

    public String getImageUrl(String originalFilename) {
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs
                            .builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(originalFilename)
                            .expiry(retentionHours, TimeUnit.HOURS)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private String getFileExtension(String filePath) {
        if (filePath == null) {
            return null;
        }
        String[] fileNameParts = filePath.split("\\.");
        String fileName = fileNameParts[fileNameParts.length - 1];
        // Check if the filename contains invalid characters
        if (fileName.contains("..")) {
            throw new RuntimeException(
                    "Sorry! Filename contains invalid path sequence " + fileName);
        }
        return fileName;
    }


}
