package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Tokenisers.BPE;
import main.java.ie.atu.forge.Tokenisers.Ngram;
import main.java.ie.atu.forge.Tokenisers.Shingle;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TokenisationService {
    private final BPE tokeniser;

    public TokenisationService() throws IOException {
        this.tokeniser = new BPE();
        String pathToTokens = "src/main/resources/bpe_vocab_hex.json";
        tokeniser.loadVocabFromJsonHex(pathToTokens);
    }

    public String[] ngrams(String text, int window) {
        return Ngram.tokenise(text, window);
    }

    public String[] shingles(String text, int window) {
        return Shingle.tokenise(text, window);
    }

    public int[] encode(String text) {
        return tokeniser.encode(text);
    }

    public String decode(int[] tokens) {
        return tokeniser.decode(tokens);
    }
}