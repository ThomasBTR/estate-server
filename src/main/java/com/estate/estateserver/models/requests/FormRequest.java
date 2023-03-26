package com.estate.estateserver.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormRequest implements Serializable {
    @Schema(type = "string", example = "Nice house", description = "offer name")
    @NotNull
    String name;
    @Schema(type = "double", example = "1", description = "offer surface in local space unit")
    @NotNull
    Double surface;
    @Schema(type = "integer", example = "1", description = "offer price in local currency")
    @NotNull
    double price;
    @Schema(type = "picture", example = "1", description = "offer pictures")
    @NotNull
    private MultipartFile picture;
    @Schema(type = "string", format = "binary", example = "1", description = "offer description")
    @NotNull
    String description;
}
