package com.estate.estateserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Estate API",
                version = "1.0.0",
                description = "Back-end access API design for ChâTop front-end Angular app",
                contact = @Contact(
                        name = "ChâTop developer's team",
                        email = "thomas.berthomier.dev@gmail.com"
                )
        ),
        tags = {
                @Tag(name = "Rentals", description = "Rentals API"),
                @Tag(name = "Rental per id", description = "Rental per id API"),
                @Tag(name = "Authentication", description = "The Authentication API"),
                @Tag(name = "User Information", description = "User info API, part of the Authentication")
        }
)
@SecurityScheme(name = "token", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class EstateServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstateServerApplication.class, args);
    }

}
