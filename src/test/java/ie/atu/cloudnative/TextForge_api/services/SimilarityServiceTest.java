package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.AlignmentFree.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SimilarityServiceTest {

    @Mock
    private MinHash minHash; // The library dependency

    @InjectMocks
    private SimilarityService similarityService; // The service we are testing

    @Test
    void minHashJaccard_delegateToLibrary() {
        // ARRANGE: Set a dummy value that the library doesn't actually produce
        double dummyReturn = 0.999;
        when(minHash.jaccard(any(), any())).thenReturn(dummyReturn);

        // ACT
        double result = similarityService.minHashJaccard(new String[]{"A"}, new String[]{"B"});

        // ASSERT: Proves the "Working" connection
        assertThat(result).isEqualTo(dummyReturn); // Did it pass the data back?
        verify(minHash, times(1)).jaccard(any(), any()); // Did it actually call the library?
    }

    @Test
    void minHashSorensen_delegateToLibrary() {
        // ARRANGE
        double dummyReturn = 0.554433;
        when(minHash.sorensenDice(any(), any())).thenReturn(dummyReturn);

        // ACT
        double result = similarityService.minHashSorensen(new String[]{"input1"}, new String[]{"input2"});

        // ASSERT
        assertThat(result).isEqualTo(dummyReturn);
        verify(minHash).sorensenDice(any(), any());
    }



    @Test
    void cosine_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Cosine.class)) {
            // ARRANGE
            double dummy = 0.6543;
            mockedStatic.when(() -> Cosine.similarity(any(int[].class), any(int[].class))).thenReturn(dummy);

            // ACT
            double result = similarityService.cosine(new int[]{1}, new int[]{1});

            // ASSERT
            assertThat(result).isEqualTo(dummy);
            mockedStatic.verify(() -> Cosine.similarity(any(int[].class), any(int[].class)));
        }
    }

    @Test
    void sorensen_delegateToLibrary() {
        try (var mockedStatic = mockStatic(SorensenDice.class)) {
            // ARRANGE
            double dummy = 0.6543;
            mockedStatic.when(() -> SorensenDice.similarity(any(Set.class), any(Set.class))).thenReturn(dummy);

            // ACT
            double result = similarityService.sorensen(new String[]{"A"}, new String[]{"B"});

            // ASSERT
            assertThat(result).isEqualTo(dummy);
            mockedStatic.verify(() -> SorensenDice.similarity(any(Set.class), any(Set.class)));
        }
    }

    @Test
    void tversky_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Tversky.class)) {
            // ARRANGE
            double dummy = 0.6543;
            mockedStatic.when(() -> Tversky.similarity(any(Set.class), any(Set.class), anyDouble(), anyDouble())).thenReturn(dummy);

            // ACT
            double result = similarityService.tversky(new String[]{"A"}, new String[]{"B"}, 0.5, 0.5);

            // ASSERT
            assertThat(result).isEqualTo(dummy);
            mockedStatic.verify(() -> Tversky.similarity(any(Set.class), any(Set.class), anyDouble(), anyDouble()));
        }
    }
}