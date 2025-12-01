package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.AlignmentFree.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SimilarityService {
    private final MinHash minHash;
    private final double defaultP = 0.5; // A default p-value to use is none is provided for Minkowski.
    private final double defaultA = 0.75; // A default a-value to use is none is provided for Tversky.
    private final double defaultB = 0.75; // A default b-value to use is none is provided for Tversky.

    public SimilarityService() {
        this.minHash = new MinHash(128);
    }

    public double similarity(int[] v1, int[] v2, String method, Double p) {
        if(v1 == null) v1 = new int[]{};
        if(v2 == null) v2 = new int[]{};

        return switch (method) {
            case "canberra" -> Canberra.distance(v1, v2);
            case "chebyshev" -> Chebyshev.distance(v1, v2);
            case "cosine" -> Cosine.distance(v1, v2);
            case "euclidean" -> Euclidean.distance(v1, v2);
            case "manhattan" -> Manhattan.distance(v1, v2);
            case "minkowski" -> Minkowski.distance(v1, v2, p == null ? defaultP : p);
            default -> 0.0d;
        };
    }

    public double setSimilarity(Set<String> s1, Set<String> s2, String method, Double a, Double b) {
        if(s1 == null) s1 = new HashSet<>();
        if(s2 == null) s2 = new HashSet<>();

        return switch (method) {
            case "jaccard" -> Jaccard.distance(s1, s2);
            case "sorensendice" -> SorensenDice.distance(s1, s2);
            case "tversky" -> Tversky.distance(s1, s2, a == null ? defaultA : a , b == null ? defaultB : b);
            case "minhash", "minhashjaccard" -> minHash.jaccard(s1, s2);
            case "minhashsorensen" -> minHash.sorensenDice(s1, s2);
            default -> 0.0d;
        };
    }
}
