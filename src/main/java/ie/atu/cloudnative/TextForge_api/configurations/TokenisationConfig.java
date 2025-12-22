package ie.atu.cloudnative.TextForge_api.configurations;

import main.java.ie.atu.forge.Tokenisers.BPE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class TokenisationConfig {
    @Bean
    public BPE bpe() throws IOException {
        BPE tokeniser = new BPE();
        String pathToTokens = "src/main/resources/bpe_vocab_hex.json";
        tokeniser.loadVocabFromJsonHex(pathToTokens);
        return tokeniser;
    }
}
