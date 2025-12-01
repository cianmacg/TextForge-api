package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.AlignmentFree.*;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SimilarityService {
    private final MinHash minHash;

    public SimilarityService(MinHash minHash) {
        this.minHash = minHash;
        this.minHash.regenerateFuncs(128);
    }

    public double similarity(int[] v1, int[] v2, String method, double p) {
        return switch (method) {
            case "canberra" -> Canberra.distance(v1, v2);
            case "chebyshev" -> Chebyshev.distance(v1, v2);
            case "cosine" -> Cosine.distance(v1, v2);
            case "euclidean" -> Euclidean.distance(v1, v2);
            case "minkowski" -> Minkowski.distance(v1, v2, 0.5);
            case "manhattan" -> Manhattan.distance(v1, v2);
            default -> 0.0d;
        };
    }

    public double SetSimilarity(Set<String> s1, Set<String> s2, String method, double a, double b) {
        return switch (method) {
            case "jaccard" -> Jaccard.distance(s1, s2);
            case "sorensen-dice" -> SorensenDice.distance(s1, s2);
            case "tversky" -> Tversky.distance(s1, s2, a, b);
            case "minhash", "minhashjaccard" -> minHash.jaccard(s1, s2);
            case "minhashsorensen" -> minHash.sorensenDice(s1, s2);
            default -> 0.0d;
        };
    }
}
