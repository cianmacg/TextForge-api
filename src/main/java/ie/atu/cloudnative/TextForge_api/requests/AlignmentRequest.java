package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotNull;

public record AlignmentRequest(
        @NotNull(message = "String 's1' is a required field.")
        String s1,

        @NotNull(message = "String 's2' is a required field.")
        String s2
) {}