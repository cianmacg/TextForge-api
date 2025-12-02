package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TverskyRequest (
        @NotNull(message = "String 's1' is a required field.")
        String[] s1,

        @NotNull(message = "String 's2' is a required field.")
        String[] s2,
        Double a,
        Double b
){}
