package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Tokenisers.BPE;
import main.java.ie.atu.forge.Tokenisers.Ngram;
import main.java.ie.atu.forge.Tokenisers.Shingle;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TokenisationService {
    private final BPE tokeniser;

    private final ArrayList<String> ngramNames;
    private final ArrayList<String> shingleNames;
    private final ArrayList<String> bpeNames;

    public TokenisationService() throws IOException {
        this.tokeniser = new BPE();
        String pathToTokens = "src/main/resources/bpe_vocab_hex.json";
        tokeniser.loadVocabFromJsonHex(pathToTokens);

        this.ngramNames  = new ArrayList<>(Arrays.asList("default", "ngram", "ngrams"));
        this.shingleNames = new ArrayList<>(Arrays.asList("shingle", "shingles"));
        this.bpeNames = new ArrayList<>(Arrays.asList("bpe", "byte-pair encoding", "byte pair encoding"));
    }

    public String[] tokenise(String text, String method, int window) {
        String type = method.toLowerCase();

        if(ngramNames.contains(type)) {
            return Ngram.tokenise(text, window);
        }
        else if(shingleNames.contains(type)) {
            return Shingle.tokenise(text, window);
        }
        else if(bpeNames.contains(type)) {
            return Arrays.stream(tokeniser.encode(text))
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
        }

        return null;
    }

    public int[] encode(String text) {
        return tokeniser.encode(text);
    }

    public String decode(int[] tokens) {
        return tokeniser.decode(tokens);
    }
}