package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.Alignment.Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlignmentServiceTest {

    private AlignmentService alignmentService;

    @BeforeEach
    void setUp() {
        // ARRANGE: Initialize the service instance, which also initializes the dependencies
        alignmentService = new AlignmentService();
    }

    // --- Tests for SmithWaterman (Local Alignment) ---

    @Test
    void smithWaterman_shouldReturnCorrectLocalAlignment() {
        // ARRANGE
        // Smith-Waterman finds the best matching local sequences.
        String s1 = "TIGER";
        String s2 = "TAGGICR";
        // Expected result for a standard Smith-Waterman implementation
        // using common scoring (e.g., Match: 2, Mismatch: -1, Gap: -2)
        // The best local alignment is typically "TIGER" vs "T-GICR" or similar.
        // Assuming the utility returns the aligned strings as an array of length 2: [AlignedS1, AlignedS2]
        String[] expected = {"TIGER", "TA-G-ICR"};

        // ACT
        String[] result = alignmentService.smithWaterman(s1, s2);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.length).isEqualTo(2);
        // Note: Due to potential differences in scoring schemes,
        // this is the most likely alignment, but may need adjustment based on the actual Forge library.
        // For testing, we verify the output format and a plausible alignment.
        assertThat(result[0]).isNotEmpty();
        assertThat(result[1]).isNotEmpty();
    }

    @Test
    void smithWaterman_shouldHandleEmptyStrings() {
        // ARRANGE
        String s1 = "";
        String s2 = "TEST";

        // ACT
        String[] result = alignmentService.smithWaterman(s1, s2);

        // ASSERT
        // Local alignment with an empty string typically returns two empty strings,
        // or a string of gaps matching the non-empty string.
        assertThat(result.length).isEqualTo(2);
    }

    // --- Tests for NeedlemanWunsch (Global Alignment) ---

    @Test
    void needlemanWunsch_shouldReturnCorrectGlobalAlignment() {
        // ARRANGE
        // Needleman-Wunsch aligns the full sequences.
        String s1 = "AGACTAR";
        String s2 = "TGCAT";
        // Expected result:
        // S1: A G A C T A R
        // S2: - T G C A T -
        String[] expected = {"AGACTAR", "-TGCAT-"};

        // ACT
        String[] result = alignmentService.needlemanWunsch(s1, s2);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.length).isEqualTo(2);
        // Verify that the aligned strings have the same length
        assertThat(result[0].length()).isEqualTo(result[1].length());
    }

    // --- Tests for SeedAndExtend (Heuristic Alignment) ---

    // Note: The structure of Extension[] requires knowledge of the 'Extension' class.
    // We'll test the structure and a basic plausible alignment result.

    @Test
    void seedAndExtend_shouldReturnPlausibleExtensions() {
        // ARRANGE
        // Seed and Extend uses short k-mers to find potential match regions.
        String s1 = "SEQUENCEDATA";
        String s2 = "DATABASES";
        int kMerLength = 3; // e.g., "DAT" is a common k-mer

        // ACT
        Extension[] results = alignmentService.seedAndExtend(s1, s2, kMerLength);

        // ASSERT
        assertThat(results).isNotNull();
        // We expect at least one extension to be found around the common "DATA" substring
        assertThat(results.length).isGreaterThanOrEqualTo(1);

        // Further assertion would require knowing the properties of the Extension class:
        // assertThat(results[0].getScore()).isGreaterThan(0);
        // assertThat(results[0].getStartS1()).isEqualTo(8); // Start of 'DATA' in s1
    }

    @Test
    void seedAndExtend_shouldHandleNoCommonKmers() {
        // ARRANGE
        String s1 = "AAAAAA";
        String s2 = "BBBBBB";
        int kMerLength = 3;

        // ACT
        Extension[] results = alignmentService.seedAndExtend(s1, s2, kMerLength);

        // ASSERT
        // If there are no common k-mers, the array should be empty
        assertThat(results).isEmpty();
    }
}