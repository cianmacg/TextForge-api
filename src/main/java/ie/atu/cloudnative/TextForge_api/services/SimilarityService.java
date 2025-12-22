package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.AlignmentFree.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SimilarityService {
    private final MinHash minHash;

    public SimilarityService(MinHash mh) {
        this.minHash = mh;
    }

    public Double cosine(int[] v1, int[] v2) {
        return Cosine.similarity(v1, v2);
    }

    public Double jaccard(String[] s1, String[] s2) {
        return Jaccard.similarity(convertToSet(s1), convertToSet(s2));
    }

    public Double sorensen(String[] s1, String[] s2) {
        return SorensenDice.similarity(convertToSet(s1), convertToSet(s2));
    }

    public Double tversky(String[] s1, String[] s2, double a, double b) {
        return Tversky.similarity(convertToSet(s1), convertToSet(s2), a, b);
    }

    // MinHash can approximate either Jaccard or Sorensen-Dice.
    public Double minHashJaccard(String[] s1, String[] s2) {
        return minHash.jaccard(convertToSet(s1), convertToSet(s2));
    }

    public Double minHashSorensen(String[] s1, String[] s2) {
        return minHash.sorensenDice(convertToSet(s1), convertToSet(s2));
    }

    private Set<String> convertToSet(String[] s) {
        return new HashSet<>(List.of(s));
    }
}
