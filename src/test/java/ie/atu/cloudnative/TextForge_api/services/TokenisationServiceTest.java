package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Tokenisers.BPE;
import main.java.ie.atu.forge.Tokenisers.Ngram;
import main.java.ie.atu.forge.Tokenisers.Shingle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenisationServiceTest {

    @Mock
    private BPE bpe; // Mocking the instance dependency

    @InjectMocks
    private TokenisationService tokenisationService; // Injects the mock BPE automatically

    // --- BPE (Instance) Tests ---

    @Test
    void encode_delegateToLibrary() {
        // ARRANGE
        int[] dummyTokens = {1, 2, 3};
        when(bpe.encode(anyString())).thenReturn(dummyTokens);

        // ACT
        int[] result = tokenisationService.encode("hello");

        // ASSERT
        assertThat(result).isSameAs(dummyTokens);
        verify(bpe).encode("hello");
    }

    @Test
    void decode_delegateToLibrary() {
        // ARRANGE
        String dummyText = "decoded text";
        when(bpe.decode(any(int[].class))).thenReturn(dummyText);

        // ACT
        String result = tokenisationService.decode(new int[]{1, 2, 3});

        // ASSERT
        assertThat(result).isEqualTo(dummyText);
        verify(bpe).decode(any(int[].class));
    }

    // --- Ngram & Shingle (Static) Tests ---

    @Test
    void ngrams_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Ngram.class)) {
            // ARRANGE
            String[] dummyOutput = {"ban", "ana"};
            mockedStatic.when(() -> Ngram.tokenise(anyString(), anyInt())).thenReturn(dummyOutput);

            // ACT
            String[] result = tokenisationService.ngrams("banana", 3);

            // ASSERT
            assertThat(result).isSameAs(dummyOutput);
            mockedStatic.verify(() -> Ngram.tokenise(eq("banana"), eq(3)));
        }
    }

    @Test
    void shingles_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Shingle.class)) {
            // ARRANGE
            String[] dummyOutput = {"hello worl", "ello world"};
            mockedStatic.when(() -> Shingle.tokenise(anyString(), anyInt())).thenReturn(dummyOutput);

            // ACT
            String[] result = tokenisationService.shingles("hello world", 10);

            // ASSERT
            assertThat(result).isSameAs(dummyOutput);
            mockedStatic.verify(() -> Shingle.tokenise(eq("hello world"), eq(10)));
        }
    }
}