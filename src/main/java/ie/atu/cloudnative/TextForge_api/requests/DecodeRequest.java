package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DecodeRequest(
        @NotNull(message = "Integer array tokens is a required field.")
        @NotBlank(message = "Integer array tokens must not be empty.")
        int[] tokens
) {}
