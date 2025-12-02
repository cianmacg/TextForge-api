package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotNull;

public record SetSimilarityRequest(
        @NotNull(message = "String array 's1' is a required field.")
        String[] s1,

        @NotNull(message = "String array 's2' is a required field.")
        String[] s2
) {}
