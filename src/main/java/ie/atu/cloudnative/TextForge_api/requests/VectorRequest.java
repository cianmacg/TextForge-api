package ie.atu.cloudnative.TextForge_api.requests;

public record VectorRequest(
   String[] words,
   String sentence
) {}
