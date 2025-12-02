package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VectorRequest(
        @NotNull(message = "String array 'documents' is a required field (may my individual words).")
        @NotBlank(message = "String array 'documents' must not be empty.")
        String[] documents
) {}
