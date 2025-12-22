package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.Alignment.*;
import main.java.ie.atu.forge.Similarity.AlignmentFree.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class DistanceServiceTest {

    @InjectMocks
    private DistanceService distanceService;

    // --- Vector Distance Methods (AlignmentFree) ---

    @Test
    void cosine_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Cosine.class)) {
            double dummy = 0.5;
            mockedStatic.when(() -> Cosine.distance(any(int[].class), any(int[].class))).thenReturn(dummy);
            assertThat(distanceService.cosine(new int[]{1}, new int[]{1})).isEqualTo(dummy);
            mockedStatic.verify(() -> Cosine.distance(any(int[].class), any(int[].class)));
        }
    }

    @Test
    void canberra_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Canberra.class)) {
            double dummy = 1.23;
            mockedStatic.when(() -> Canberra.distance(any(int[].class), any(int[].class))).thenReturn(dummy);
            assertThat(distanceService.canberra(new int[]{1}, new int[]{2})).isEqualTo(dummy);
            mockedStatic.verify(() -> Canberra.distance(any(int[].class), any(int[].class)));
        }
    }

    @Test
    void chebyshev_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Chebyshev.class)) {
            double dummy = 3.0;
            mockedStatic.when(() -> Chebyshev.distance(any(int[].class), any(int[].class))).thenReturn(dummy);
            assertThat(distanceService.chebyshev(new int[]{1}, new int[]{2})).isEqualTo(dummy);
            mockedStatic.verify(() -> Chebyshev.distance(any(int[].class), any(int[].class)));
        }
    }

    @Test
    void euclidean_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Euclidean.class)) {
            double dummy = 5.19;
            mockedStatic.when(() -> Euclidean.distance(any(int[].class), any(int[].class))).thenReturn(dummy);
            assertThat(distanceService.euclidean(new int[]{1}, new int[]{2})).isEqualTo(dummy);
            mockedStatic.verify(() -> Euclidean.distance(any(int[].class), any(int[].class)));
        }
    }

    @Test
    void manhattan_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Manhattan.class)) {
            int dummy = 10; // Manhattan library likely returns int
            mockedStatic.when(() -> Manhattan.distance(any(int[].class), any(int[].class))).thenReturn(dummy);
            assertThat(distanceService.manhattan(new int[]{1}, new int[]{2})).isEqualTo(10.0);
            mockedStatic.verify(() -> Manhattan.distance(any(int[].class), any(int[].class)));
        }
    }

    @Test
    void minkowski_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Minkowski.class)) {
            double dummy = 4.32;
            mockedStatic.when(() -> Minkowski.distance(any(int[].class), any(int[].class), anyDouble())).thenReturn(dummy);
            assertThat(distanceService.minkowski(new int[]{1}, new int[]{2}, 3.0)).isEqualTo(dummy);
            mockedStatic.verify(() -> Minkowski.distance(any(int[].class), any(int[].class), eq(3.0)));
        }
    }

    // --- String Distance Methods (Alignment) ---

    @Test
    void damerau_delegateToLibrary() {
        try (var mockedStatic = mockStatic(DamerauLevenshtein.class)) {
            int dummy = 1;
            mockedStatic.when(() -> DamerauLevenshtein.distance(anyString(), anyString())).thenReturn(dummy);
            assertThat(distanceService.damerau("CA", "AC")).isEqualTo(dummy);
            mockedStatic.verify(() -> DamerauLevenshtein.distance("CA", "AC"));
        }
    }

    @Test
    void levenshtein_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Levenshtein.class)) {
            int dummy = 3;
            mockedStatic.when(() -> Levenshtein.distance(anyString(), anyString())).thenReturn(dummy);
            assertThat(distanceService.levenshtein("kitten", "sitting")).isEqualTo(dummy);
            mockedStatic.verify(() -> Levenshtein.distance("kitten", "sitting"));
        }
    }

    @Test
    void hamming_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Hamming.class)) {
            int dummy = 3;
            mockedStatic.when(() -> Hamming.distance(anyString(), anyString())).thenReturn(dummy);
            assertThat(distanceService.hamming("abc", "abd")).isEqualTo(dummy);
            mockedStatic.verify(() -> Hamming.distance("abc", "abd"));
        }
    }

    @Test
    void jaro_delegateToLibrary() {
        try (var mockedStatic = mockStatic(Jaro.class)) {
            double dummy = 0.8;
            mockedStatic.when(() -> Jaro.distance(anyString(), anyString())).thenReturn(dummy);
            assertThat(distanceService.jaro("abc", "def")).isEqualTo(dummy);
            mockedStatic.verify(() -> Jaro.distance("abc", "def"));
        }
    }

    @Test
    void jaroWinkler_delegateToLibrary() {
        try (var mockedStatic = mockStatic(JaroWinkler.class)) {
            double dummy = 0.95;
            mockedStatic.when(() -> JaroWinkler.distance(anyString(), anyString())).thenReturn(dummy);
            assertThat(distanceService.jaroWinkler("test", "test")).isEqualTo(dummy);
            mockedStatic.verify(() -> JaroWinkler.distance("test", "test"));
        }
    }
}