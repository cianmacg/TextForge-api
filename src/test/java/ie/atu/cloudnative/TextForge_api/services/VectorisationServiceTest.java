package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Vectorisers.BagOfWords;
import main.java.ie.atu.forge.Vectorisers.TFIDF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VectorisationServiceTest {

    private VectorisationService vectorisationService;

    @BeforeEach
    void setUp() {
        // ARRANGE: Initialize the service instance before each test
        vectorisationService = new VectorisationService();
    }

    // --- Tests for bow() method ---

    @Test
    void bow_shouldReturnEmptyBagOfWords_whenInputIsNull() {
        // ACT
        BagOfWords result = vectorisationService.bow(null);

        // ASSERT
        assertThat(result).isNotNull();
        // Assuming BagOfWords.size() returns the number of unique words/tokens stored
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void bow_shouldReturnEmptyBagOfWords_whenInputIsEmptyArray() {
        // ARRANGE
        String[] emptyWords = {};

        // ACT
        BagOfWords result = vectorisationService.bow(emptyWords);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void bow_shouldAddWordsToBagOfWords_whenInputIsValid() {
        // ARRANGE
        String[] words = {"text", "processing", "text", "analysis"};

        // ACT
        BagOfWords result = vectorisationService.bow(words);

        // ASSERT
        assertThat(result).isNotNull();
        // Assuming BagOfWords successfully stores the words
        // We expect 3 unique words: "text", "processing", "analysis"
        assertThat(result.size()).isEqualTo(3);

        // Optional: Check specific word count (Requires a public method on BagOfWords, like getCount())
        // Assuming BagOfWords has a public map or a getCount() method for assertion:
        // assertThat(result.getCount("text")).isEqualTo(2);
        // assertThat(result.getCount("analysis")).isEqualTo(1);
    }

    // --- Tests for tfidf() method ---

    @Test
    void tfidf_shouldReturnTFIDFObject_whenInputIsNull() {
        // ACT
        TFIDF result = vectorisationService.tfidf(null);

        // ASSERT
        assertThat(result).isNotNull();
        // Assuming TFIDF documents list is empty upon creation unless documents are added
        assertThat(result.getDocumentCount()).isEqualTo(0);
    }

    @Test
    void tfidf_shouldAddAllDocumentsToTFIDF() {
        // ARRANGE
        String[] documents = {
                "This is document one",
                "Document two has different words",
                "The third document is short"
        };

        // ACT
        TFIDF result = vectorisationService.tfidf(documents);

        // ASSERT
        assertThat(result).isNotNull();
        // Assert that the TFIDF object has correctly processed all 3 documents
        // Requires a public getter like getDocumentsCount() on the TFIDF class
        assertThat(result.getDocumentCount()).isEqualTo(3);
    }

    @Test
    void tfidf_shouldHandleEmptyDocumentsArray() {
        // ARRANGE
        String[] documents = {};

        // ACT
        TFIDF result = vectorisationService.tfidf(documents);

        // ASSERT
        assertThat(result).isNotNull();
        // Check that no documents were added
        assertThat(result.getDocumentCount()).isEqualTo(0);
    }
}
