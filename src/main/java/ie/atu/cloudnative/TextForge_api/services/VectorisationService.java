package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Vectorisers.BagOfWords;
import org.springframework.stereotype.Service;

@Service
public class VectorisationService {
    public BagOfWords vectorise(String[] words, String sentence) {
        BagOfWords bow = new BagOfWords();

        if(words != null && words.length > 0) {
            bow.add(words);
        }

        if(sentence!=null && !sentence.isEmpty()) {
            bow.addSentence(sentence);
        }

        return bow;
    }
}