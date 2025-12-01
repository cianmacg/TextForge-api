package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.Alignment.*;
import main.java.ie.atu.forge.Similarity.AlignmentFree.*;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {
    public double cosine(int[] v1, int[] v2) {
        return Cosine.distance(v1, v2);
    }

    public double canberra(int[] v1, int[] v2) {
        return Canberra.distance(v1, v2);
    }

    public double chebyshev(int[] v1, int[] v2) {
        return Chebyshev.distance(v1, v2);
    }

    public double euclidean(int[] v1, int[] v2) {
        return Euclidean.distance(v1, v2);
    }

    public double manhattan(int[] v1, int[] v2) {
        return (double) Manhattan.distance(v1, v2);
    }

    public double minkowski(int[] v1, int[] v2, double p) {
        return Minkowski.distance(v1, v2, p);
    }

    public int damerau(String s1, String s2) {
        return DamerauLevenshtein.distance(s1, s2);
    }

    public int levenshtein(String s1, String s2) {
        return Levenshtein.distance(s1, s2);
    }

    public int hamming(String s1, String s2) {
        return Hamming.distance(s1, s2);
    }

    public double jaro(String s1, String s2) {
        return Jaro.distance(s1, s2);
    }

    public double jaroWinkler(String s1, String s2) {
        return JaroWinkler.distance(s1, s2);
    }
}
