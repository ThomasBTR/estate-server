package com.estate.estateserver.controllers;

import com.estate.estateserver.models.requests.MessageRequest;
import com.estate.estateserver.models.responses.MessageResponse;
import com.estate.estateserver.services.MessageServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "token")
public class MessagesController {
    private final MessageServices messageServices;

    @Operation(summary = "Post message to owner", tags = {"Messages"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Access denied",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)})
    @PostMapping(
            value = "/messages",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<MessageResponse> sendMessage(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Message request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MessageRequest.class))
            )
            MessageRequest messageRequest) {
        return ResponseEntity.ok(messageServices.postMessage(messageRequest));
    }
}
