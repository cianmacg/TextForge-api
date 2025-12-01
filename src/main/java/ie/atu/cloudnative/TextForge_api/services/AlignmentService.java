package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.Alignment.*;
import org.springframework.stereotype.Service;

@Service
public class AlignmentService {
    private final SmithWaterman smithWaterman;
    private final NeedlemanWunsch needlemanWunsch;

    public AlignmentService(SmithWaterman smithWaterman, NeedlemanWunsch needlemanWunsch) {
        this.smithWaterman = smithWaterman;
        this.needlemanWunsch = needlemanWunsch;
    }

    public Double distance(String s1, String s2, String method) {
        return switch(method) {
            case "damerau" -> (double) DamerauLevenshtein.distance(s1, s2);
            case "levenshtein" -> (double) Levenshtein.distance(s1, s2);
            case "hamming" -> (double) Hamming.distance(s1, s2);
            case "jaro" -> Jaro.distance(s1, s2);
            case "jarowinkler" -> JaroWinkler.distance(s1, s2);
            default -> null;
        };
    }

    public String[] alignment(String s1, String s2, String method, int kMerLength) {
        return switch(method) {
            case "smithwaterman" -> smithWaterman.align(s1, s2);
            case "needlemanwunsch" -> needlemanWunsch.align(s1, s2);
            default -> null;
        };
    }

    public Extension[] seedAndExtend(String s1, String s2, int kMerLength) {
        return SeedAndExtend.align(s1, s2, kMerLength);
    }
}
