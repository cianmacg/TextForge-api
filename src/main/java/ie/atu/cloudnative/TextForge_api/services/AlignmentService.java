package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.Alignment.*;
import org.springframework.stereotype.Service;

@Service
public class AlignmentService {
    private final SmithWaterman smithWaterman;
    private final NeedlemanWunsch needlemanWunsch;

    public AlignmentService() {
        this.smithWaterman = new SmithWaterman();
        this.needlemanWunsch = new NeedlemanWunsch();
    }

    public String[] smithWaterman(String s1, String s2) {
        return smithWaterman.align(s1,s2);
    }

    public String[] needlemanWunsch(String s1, String s2) {
        return needlemanWunsch.align(s1,s2);
    }

    public Extension[] seedAndExtend(String s1, String s2, int kMerLength) {
        return SeedAndExtend.align(s1, s2, kMerLength);
    }
}
