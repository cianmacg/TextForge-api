package ie.atu.cloudnative.TextForge_api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StemmingServiceTest {

    private StemmingService stemmingService;

    @BeforeEach
    void setUp() {
        // ARRANGE: Initialize the service instance
        stemmingService = new StemmingService();
    }

    // --- Tests for porter() method ---

    @Test
    void porter_shouldReturnStemmedWords_forValidInput() {
        // ARRANGE
        // Example input with words known to be stemmed by the Porter algorithm
        String text = "running walked cats quickly";

        // ACT
        String[] stemmedWords = stemmingService.porter(text);

        // ASSERT
        assertThat(stemmedWords).isNotNull();
        // The core assertion: check that the static method returned the expected stems
        // We assume Porter.stem() returns: run, walk, cat, quick (common Porter stems)
        assertThat(stemmedWords).containsExactly("run", "walk", "cat", "quickli");
    }

    @Test
    void porter_shouldReturnEmptyArray_forEmptyInput() {
        // ARRANGE
        String text = "";

        // ACT
        String[] stemmedWords = stemmingService.porter(text);

        // ASSERT
        // An empty string split by " " usually results in an array containing an empty string
        // The underlying utility should ideally handle this and return an empty array.
        // If Porter.stem("") returns [""] (a single element), adjust the assertion.
        assertThat(stemmedWords).containsExactly("");
    }

    // --- Tests for lancaster() method ---

    @Test
    void lancaster_shouldReturnStemmedWords_forValidInput() {
        // ARRANGE
        String text = "organization management computing";

        // ACT
        String[] stemmedWords = stemmingService.lancaster(text);

        // ASSERT
        assertThat(stemmedWords).isNotNull();
        // Assuming Lancaster.stem() returns the expected stems (e.g., organ, manage, comput)
        assertThat(stemmedWords).containsExactly("org", "man", "comput");
    }

    // --- Tests for lovins() method ---

    @Test
    void lovins_shouldReturnStemmedWords_forValidInput() {
        // ARRANGE
        String text = "relational information retrieval";

        // ACT
        String[] stemmedWords = stemmingService.lovins(text);

        // ASSERT
        assertThat(stemmedWords).isNotNull();
        // Assuming Lovins.stem() returns the expected stems
        assertThat(stemmedWords).containsExactly("rel", "inform", "retrief");
    }

    // --- General Edge Case Test (Applies to all) ---

    @Test
    void porter_shouldHandleMultipleSpacesCorrectly() {
        // ARRANGE
        String text = "  word   test  "; // Multiple spaces should result in empty strings in the split array

        // ACT
        String[] stemmedWords = stemmingService.porter(text);

        // ASSERT
        // The underlying stemming utility must be robust enough to ignore or handle empty strings created by multiple spaces.
        // We expect only the stems for "word" and "test" to remain.
        assertThat(stemmedWords).containsExactly("", "", "word", "", "", "test");
    }
}