package com.estate.estateserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinioServices {

    @Value(value = "${estate.minio.endpoint}:localhost:9001")
    private String endpoint;

    @Value(value = "${estate.minio.user}:estate-admin")
    private String rootUser;

    @Value(value = "${estate.minio.password}:estate-password")
    private String rootPassword;

}
