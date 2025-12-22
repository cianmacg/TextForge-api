package ie.atu.cloudnative.TextForge_api.configurations;

import main.java.ie.atu.forge.Similarity.Alignment.NeedlemanWunsch;
import main.java.ie.atu.forge.Similarity.Alignment.SmithWaterman;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlignmentConfig {
    @Bean
    public SmithWaterman smithWaterman() {
        return new SmithWaterman();
    }

    @Bean
    public NeedlemanWunsch needlemanWunsch() {
        return new NeedlemanWunsch();
    }
}
