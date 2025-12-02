package ie.atu.cloudnative.TextForge_api.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;

public class TokenisationServiceTest {

    // Instantiate the service once for all tests, simulating Spring context initialization
    private static TokenisationService tokenisationService;

    @BeforeAll
    static void setup() throws IOException {
        // This simulates the Spring context loading the bean and running the constructor
        tokenisationService = new TokenisationService();
    }

    @Test
    void shouldEncodeAndDecodeTextCorrectly() {
        // ARRANGE
        String originalText = "The quick brown fox jumps over the lazy dog.";

        // ACT
        int[] encodedTokens = tokenisationService.encode(originalText);
        String decodedText = tokenisationService.decode(encodedTokens);

        // ASSERT
        // The core test: round trip should yield the original text
        assertThat(decodedText).isEqualTo(originalText);
        assertThat(encodedTokens).isNotEmpty();
    }

    @Test
    void shouldHandleEmptyStringEncoding() {
        // ARRANGE
        String originalText = "";

        // ACT
        int[] encodedTokens = tokenisationService.encode(originalText);

        // ASSERT
        // Encoding an empty string should result in an empty token array
        assertThat(encodedTokens).isEmpty();
    }

    @Test
    void ngrams_shouldReturnCorrectTokens_forValidInput() {
        // ARRANGE
        String text = "banana";
        int window = 3;
        String[] expected = new String[]{"ban", "ana", "nan", "ana"};

        // ACT
        String[] result = tokenisationService.ngrams(text, window);

        // ASSERT
        assertThat(result).containsExactly(expected);
    }

    @Test
    void shingles_shouldReturnCorrectTokens_forValidInput() {
        // ARRANGE
        String text = "hello world";
        int window = 5;
        // Assuming Shingle utility logic splits based on spaces first, then tokenises words
        String[] expected = new String[]{};

        // ACT
        String[] result = tokenisationService.shingles(text, window);

        // ASSERT
        assertThat(result).containsExactly(expected);
    }
}