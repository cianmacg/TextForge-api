package ie.atu.cloudnative.TextForge_api.configurations;

import main.java.ie.atu.forge.Similarity.AlignmentFree.MinHash;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimilarityConfig {
    @Bean
    public MinHash minHash() {
        return new MinHash(128);
    }
}
