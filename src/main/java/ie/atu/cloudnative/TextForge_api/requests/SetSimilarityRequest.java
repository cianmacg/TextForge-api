package ie.atu.cloudnative.TextForge_api.requests;

public record SetSimilarityRequest(
        String[] s1,
        String[] s2
) {}
