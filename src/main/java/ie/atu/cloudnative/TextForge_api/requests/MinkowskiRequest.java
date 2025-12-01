package ie.atu.cloudnative.TextForge_api.requests;

public record MinkowskiRequest(
        int[] v1,
        int[] v2,
        double p
) {}
