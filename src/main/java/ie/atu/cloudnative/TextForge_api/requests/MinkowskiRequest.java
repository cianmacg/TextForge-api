package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotNull;

public record MinkowskiRequest(
        @NotNull(message = "Integer array 'v1' is a required field.")
        int[] v1,

        @NotNull(message = "Integer array 'v2' is a required field.")
        int[] v2,
        Double p
) {}
