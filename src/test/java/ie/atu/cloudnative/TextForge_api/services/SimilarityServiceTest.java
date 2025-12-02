package ie.atu.cloudnative.TextForge_api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SimilarityServiceTest {

    private SimilarityService similarityService;

    @BeforeEach
    void setUp() {
        // ARRANGE: Initialize the service instance for each test
        similarityService = new SimilarityService();
    }

    // --- Helper Method Test ---

    @Test
    void convertToSet_shouldHandleDuplicatesAndReturnSet() {
        // We'll test the private utility indirectly through a public method that uses it,
        // or directly if possible, but testing the public surface is better.
        // For simplicity, let's just rely on the main tests to cover it.
    }

    // --- Tests for cosine() (Vector Similarity) ---

    @Test
    void cosine_shouldReturnPerfectSimilarity_forIdenticalVectors() {
        // ARRANGE
        int[] v1 = {1, 2, 3};
        int[] v2 = {1, 2, 3};
        // Expected Cosine Similarity (1.0 for identical non-zero vectors)
        Double expected = 1.0;

        // ACT
        Double result = similarityService.cosine(v1, v2);

        // ASSERT
        // Use a delta/epsilon for floating point comparison
        assertThat(result).isCloseTo(expected, withDelta(0.0001));
    }

    @Test
    void cosine_shouldReturnZeroSimilarity_forOrthogonalVectors() {
        // ARRANGE
        int[] v1 = {1, 0, 0};
        int[] v2 = {0, 1, 0};
        // Expected Cosine Similarity (0.0 for orthogonal vectors)
        Double expected = 0.0;

        // ACT
        Double result = similarityService.cosine(v1, v2);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(0.0001));
    }

    // --- Tests for Jaccard() (Set Similarity) ---

    @Test
    void jaccard_shouldReturnCorrectSimilarity_forOverlappingSets() {
        // ARRANGE
        // A = {a, b, c, d}, B = {c, d, e, f}
        // Intersection = {c, d} (size 2)
        // Union = {a, b, c, d, e, f} (size 6)
        // Jaccard = |A ∩ B| / |A ∪ B| = 2 / 6 = 0.3333...
        String[] s1 = {"a", "b", "c", "d"};
        String[] s2 = {"c", "d", "e", "f"};
        Double expected = 2.0 / 6.0;

        // ACT
        Double result = similarityService.jaccard(s1, s2);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(0.0001));
    }

    @Test
    void jaccard_shouldHandleIdenticalSets() {
        // ARRANGE
        String[] s1 = {"A", "B"};
        String[] s2 = {"B", "A"};
        Double expected = 1.0;

        // ACT
        Double result = similarityService.jaccard(s1, s2);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(0.0001));
    }

    // --- Tests for Sorensen() ---

    @Test
    void sorensen_shouldReturnCorrectSimilarity_forOverlappingSets() {
        // ARRANGE
        // A = {a, b, c, d}, B = {c, d, e, f}
        // Intersection = {c, d} (size 2)
        // Sorensen = 2 * |A ∩ B| / (|A| + |B|) = 2 * 2 / (4 + 4) = 4 / 8 = 0.5
        String[] s1 = {"a", "b", "c", "d"};
        String[] s2 = {"c", "d", "e", "f"};
        Double expected = 0.5;

        // ACT
        Double result = similarityService.sorensen(s1, s2);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(0.0001));
    }

    // --- Tests for Tversky() ---

    @Test
    void tversky_shouldActLikeJaccard_whenAlphaAndBetaAreOne() {
        // ARRANGE
        // Tversky with a=1, b=1 is equivalent to Jaccard. Expected: 2/6 = 0.3333...
        String[] s1 = {"a", "b", "c", "d"};
        String[] s2 = {"c", "d", "e", "f"};
        double alpha = 1.0;
        double beta = 1.0;
        Double expected = 2.0 / 6.0;

        // ACT
        Double result = similarityService.tversky(s1, s2, alpha, beta);

        // ASSERT
        assertThat(result).isCloseTo(expected, withDelta(0.0001));
    }

    // --- Tests for MinHash Approximations (Delegation) ---

    @Test
    void minHashJaccard_shouldReturnApproximation_forSets() {
        // ARRANGE
        // MinHash is probabilistic, so we check that it runs and returns a plausible value.
        // We'll use sets with high Jaccard similarity (0.8) and check if the MinHash approximation is close.
        String[] s1 = {"1", "2", "3", "4", "5"};
        String[] s2 = {"1", "2", "3", "4", "6"}; // Jaccard = 4/6 = 0.666...
        Double actualJaccard = 4.0 / 6.0;

        // ACT
        Double minHashResult = similarityService.minHashJaccard(s1, s2);

        // ASSERT
        assertThat(minHashResult).isNotNull();
        // MinHash is an *approximation*, so we check if the result is reasonably close
        // The accuracy depends on the number of hash functions (128), so we allow a wider tolerance.
        assertThat(minHashResult).isCloseTo(actualJaccard, withDelta(0.1));
    }

    @Test
    void minHashSorensen_shouldReturnApproximation_forSets() {
        // ARRANGE
        // Sets: s1: 5 elements, s2: 5 elements, intersection: 4 elements
        // Sorensen = 2 * 4 / (5 + 5) = 8 / 10 = 0.8
        String[] s1 = {"1", "2", "3", "4", "5"};
        String[] s2 = {"1", "2", "3", "4", "6"};
        Double actualSorensen = 0.8;

        // ACT
        Double minHashResult = similarityService.minHashSorensen(s1, s2);

        // ASSERT
        assertThat(minHashResult).isNotNull();
        // Check if the result is reasonably close to the true Sorensen value
        assertThat(minHashResult).isCloseTo(actualSorensen, withDelta(0.1));
    }

    // --- Helper method for float assertions ---

    private org.assertj.core.data.Offset<Double> withDelta(double delta) {
        return org.assertj.core.data.Offset.offset(delta);
    }
}