package ie.atu.cloudnative.TextForge_api.requests;

public record AlignmentRequest(
        String s1,
        String s2,
        String method
) {}