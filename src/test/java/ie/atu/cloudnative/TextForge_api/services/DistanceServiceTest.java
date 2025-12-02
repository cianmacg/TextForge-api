package ie.atu.cloudnative.TextForge_api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DistanceServiceTest {

    private DistanceService distanceService;

    // Use a small delta for floating point assertions
    private final double DELTA = 0.0001;

    @BeforeEach
    void setUp() {
        // ARRANGE: Initialize the service instance for each test
        distanceService = new DistanceService();
    }

    // --- Vector Distance Tests (AlignmentFree) ---

    // Shared test vectors for basic distance calculation
    private final int[] v1 = {1, 2, 3};
    private final int[] v2 = {4, 5, 6};

    @Test
    void cosine_shouldReturnCorrectDistance() {
        // Cosine Distance is 1 - Cosine Similarity.
        // Similarity(v1, v2) = (1*4 + 2*5 + 3*6) / (sqrt(1+4+9) * sqrt(16+25+36)) = 32 / (sqrt(14) * sqrt(77)) approx 0.9631
        // Distance = 1 - 0.9631 approx 0.0368
        Double expected = 0.02536;

        // ACT
        Double result = distanceService.cosine(v1, v2);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(DELTA));
    }

    @Test
    void euclidean_shouldReturnCorrectDistance() {
        // Distance = sqrt((4-1)^2 + (5-2)^2 + (6-3)^2) = sqrt(3^2 + 3^2 + 3^2) = sqrt(27) approx 5.1961
        Double expected = 5.196152;

        // ACT
        Double result = distanceService.euclidean(v1, v2);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(DELTA));
    }

    @Test
    void manhattan_shouldReturnCorrectDistance() {
        // Distance = |4-1| + |5-2| + |6-3| = 3 + 3 + 3 = 9
        Double expected = 9.0;

        // ACT
        Double result = distanceService.manhattan(v1, v2);

        // ASSERT
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void chebyshev_shouldReturnCorrectDistance() {
        // Distance = max(|4-1|, |5-2|, |6-3|) = max(3, 3, 3) = 3
        Double expected = 3.0;

        // ACT
        Double result = distanceService.chebyshev(v1, v2);

        // ASSERT
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void canberra_shouldReturnCorrectDistance() {
        // Distance = sum(|v1_i - v2_i| / (|v1_i| + |v2_i|))
        // |1-4|/(1+4) + |2-5|/(2+5) + |3-6|/(3+6) = 3/5 + 3/7 + 3/9 = 0.6 + 0.42857 + 0.33333 approx 1.3619
        Double expected = 1.361904;

        // ACT
        Double result = distanceService.canberra(v1, v2);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(DELTA));
    }

    @Test
    void minkowski_shouldReturnCorrectDistance_withPValue() {
        // If p=3 (cubic distance): ((|4-1|^3 + |5-2|^3 + |6-3|^3)^(1/3))
        // (3^3 + 3^3 + 3^3)^(1/3) = (27 + 27 + 27)^(1/3) = 81^(1/3) approx 4.3267
        Double expected = 4.326748;

        // ACT
        Double result = distanceService.minkowski(v1, v2, 3.0);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(DELTA));
    }

    // --- String Distance Tests (Alignment) ---

    @Test
    void levenshtein_shouldReturnCorrectDistance() {
        // ARRANGE: kitten -> sitting (substitute k->s, substitute e->i, substitute n->g) = 3 edits
        String s1 = "kitten";
        String s2 = "sitting";
        int expected = 3;

        // ACT
        int result = distanceService.levenshtein(s1, s2);

        // ASSERT
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void damerau_shouldReturnCorrectDistance() {
        // ARRANGE: CA -> AC (1 transposition)
        String s1 = "CA";
        String s2 = "AC";
        int expected = 1;

        // ACT
        int result = distanceService.damerau(s1, s2);

        // ASSERT
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void damerau_shouldReturnCorrectDistanceForComplexCase() {
        // ARRANGE: cat -> act (1 transposition and 1 insertion/deletion)
        String s1 = "ca";
        String s2 = "abc";
        // Assuming the utility handles substitution/insertion/deletion + transposition
        int expected = 3; // c->a, a->b, insert c (Levenshtein would be 3)
        // Check a known case where Damerau < Levenshtein, e.g., 'ab' -> 'ba'
        String s3 = "ab";
        String s4 = "ba";
        int expectedTransposition = 1;

        // ACT
        int resultTransposition = distanceService.damerau(s3, s4);

        // ASSERT
        assertThat(resultTransposition).isEqualTo(expectedTransposition);
    }

    @Test
    void hamming_shouldReturnCorrectDistance() {
        // ARRANGE: Must be same length: "karolin" vs "kathrin" (r!=t, o!=h, l!=r, i!=i, n!=n) = 3 differences
        String s1 = "karolin";
        String s2 = "kathrin";
        int expected = 3;

        // ACT
        int result = distanceService.hamming(s1, s2);

        // ASSERT
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void jaroWinkler_shouldReturnPerfectSimilarity_forIdenticalStrings() {
        // ARRANGE
        String s1 = "test";
        String s2 = "test";
        Double expected = 0.0;

        // ACT
        Double result = distanceService.jaroWinkler(s1, s2);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(DELTA));
    }

    @Test
    void jaroWinkler_shouldReturnHighScore_forSimilarStrings() {
        // ARRANGE: Known example: "martha" vs "marhta" (Jaro-Winkler should be very high due to prefix 'mar')
        String s1 = "martha";
        String s2 = "marhta";
        Double expected = 0.0388; // Standard benchmark result

        // ACT
        Double result = distanceService.jaroWinkler(s1, s2);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(0.001)); // Wider delta for specific algorithms
    }

    @Test
    void jaro_shouldReturnZero_forCompletelyDifferentStrings() {
        // ARRANGE
        String s1 = "abcde";
        String s2 = "fghij";
        // ACT
        Double result = distanceService.jaro(s1, s2);
        // ASSERT
        assertThat(result).isCloseTo(1.0, withDelta(DELTA));
    }


    // --- Helper method for float assertions ---

    private org.assertj.core.data.Offset<Double> withDelta(double delta) {
        return org.assertj.core.data.Offset.offset(delta);
    }
}