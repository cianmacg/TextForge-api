package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TokenRequest(
        @NotNull(message = "String 'text' is a required field.")
        @NotBlank(message = "String 'text' must not be empty.")
        String text,
        Integer window
) {}
