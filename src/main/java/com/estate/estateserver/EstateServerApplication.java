package com.estate.estateserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Estate API", version = "1.0", description = "Estate API"))
@SecurityScheme(name = "token", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class EstateServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstateServerApplication.class, args);
    }

}
