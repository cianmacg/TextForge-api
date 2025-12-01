package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotBlank;

public record StemRequest(
        @NotBlank(message = "Text must not be empty.")
        String text
) {}
