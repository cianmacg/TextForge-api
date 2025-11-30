package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TokensRequest(
        @NotNull(message = "Text must be present.")
        @NotBlank(message = "Text must not be empty.")
        String text,

        String method,
        Integer window
) {}
