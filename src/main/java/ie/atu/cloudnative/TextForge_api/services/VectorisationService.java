package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Vectorisers.BagOfWords;
import main.java.ie.atu.forge.Vectorisers.TFIDF;
import org.springframework.stereotype.Service;

@Service
public class VectorisationService {
    public BagOfWords bow(String[] words) {
        BagOfWords bow = new BagOfWords();

        if(words != null && words.length > 0) {
            bow.add(words);
        }

        return bow;
    }

    public TFIDF tfidf(String[] documents) {
        TFIDF tf = new TFIDF();

        tf.addDocuments(documents);

        return tf;
    }
}