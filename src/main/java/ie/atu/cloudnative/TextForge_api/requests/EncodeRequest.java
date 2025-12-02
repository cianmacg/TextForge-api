package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EncodeRequest(
        @NotNull(message = "String 'text' is required.")
        @NotBlank(message = "String 'text' must not be empty.")
        String text
) {}
