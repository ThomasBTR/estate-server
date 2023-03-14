package com.estate.estateserver.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalResponse {

    @Id
    @NotNull
    @Schema(type = "integer", example = "1", description = "offer id")
    private int id;
    @Schema(type = "string", example = "Suburban house for a good price", description = "name")
    String name;
    @Schema(type = "double", example = "24", description = "surface")
    Double surface;

    @Schema(type = "double", example = "24", description = "price")
    Double price;
    @Schema(type = "link", example = "https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg", description = "picture")
    String picture;
    @Schema(type = "string", example = "Really nice house in the suburb", description = "description")
    String description;
    @Schema(type = "double", example = "3", description = "ownerId")
    Integer ownerId;
    @Schema(type = "date", example = "2012-12-02T00:00:00", description = "creation date of the offer")
    LocalDateTime createdAt;
    @Schema(type = "date", example = "2012-12-02T00:00:00", description = "Last update date of the offer")
    LocalDateTime updatedAt;
}
