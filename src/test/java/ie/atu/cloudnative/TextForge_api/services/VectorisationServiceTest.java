package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Vectorisers.BagOfWords;
import main.java.ie.atu.forge.Vectorisers.TFIDF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VectorisationServiceTest {

    @InjectMocks
    private VectorisationService vectorisationService;

    // --- Bag of Words (BoW) Tests ---

    @Test
    void bow_shouldCreateAndPopulateBagOfWords() {
        // ARRANGE: Intercept the "new BagOfWords()" call
        try (var mockedConstruction = mockConstruction(BagOfWords.class)) {
            String[] inputWords = {"text", "processing"};

            // ACT
            BagOfWords result = vectorisationService.bow(inputWords);

            // ASSERT
            // 1. Verify a BagOfWords was created
            assertThat(mockedConstruction.constructed()).hasSize(1);

            // 2. Get the mock that was created inside the service
            BagOfWords mockBow = mockedConstruction.constructed().get(0);

            // 3. Verify the service actually called .add() with our words
            verify(mockBow).add(inputWords);
            assertThat(result).isSameAs(mockBow);
        }
    }

    @Test
    void bow_shouldNotCallAdd_whenInputIsEmpty() {
        try (var mockedConstruction = mockConstruction(BagOfWords.class)) {
            // ACT
            vectorisationService.bow(new String[]{});

            // ASSERT
            BagOfWords mockBow = mockedConstruction.constructed().get(0);
            verify(mockBow, never()).add((String[]) any());
        }
    }

    // --- TF-IDF Tests ---

    @Test
    void tfidf_shouldCreateAndPopulateTFIDF() {
        // ARRANGE: Intercept the "new TFIDF()" call
        try (var mockedConstruction = mockConstruction(TFIDF.class)) {
            String[] inputDocs = {"Doc 1", "Doc 2"};

            // ACT
            TFIDF result = vectorisationService.tfidf(inputDocs);

            // ASSERT
            assertThat(mockedConstruction.constructed()).hasSize(1);

            TFIDF mockTf = mockedConstruction.constructed().get(0);

            // Verify the service called addDocuments()
            verify(mockTf).addDocuments(inputDocs);
            assertThat(result).isSameAs(mockTf);
        }
    }

    @Test
    void tfidf_shouldHandleNullInputGracefully() {
        try (var mockedConstruction = mockConstruction(TFIDF.class)) {
            // ACT
            vectorisationService.tfidf(null);

            // ASSERT
            TFIDF mockTf = mockedConstruction.constructed().get(0);
            // Even if null, the service calls tf.addDocuments(null)
            verify(mockTf).addDocuments(null);
        }
    }
}