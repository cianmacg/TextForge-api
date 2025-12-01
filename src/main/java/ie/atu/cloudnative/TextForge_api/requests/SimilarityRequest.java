package ie.atu.cloudnative.TextForge_api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record SimilarityRequest(
        int[] v1,
        int[] v2,
        Set<String> s1,
        Set<String> s2,

        @NotNull
        @NotBlank
        String method,

        Double p,
        Double a,
        Double b
) {}
