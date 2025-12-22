package ie.atu.cloudnative.TextForge_api.services;

import main.java.ie.atu.forge.Similarity.Alignment.Extension;
import main.java.ie.atu.forge.Similarity.Alignment.NeedlemanWunsch;
import main.java.ie.atu.forge.Similarity.Alignment.SeedAndExtend;
import main.java.ie.atu.forge.Similarity.Alignment.SmithWaterman;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlignmentServiceTest {

    @Mock
    private SmithWaterman smithWaterman; // Library Dependency 1

    @Mock
    private NeedlemanWunsch needlemanWunsch; // Library Dependency 2

    @InjectMocks
    private AlignmentService alignmentService; // Service under test

    // --- Tests for SmithWaterman (Instance Delegation) ---

    @Test
    void smithWaterman_delegateToLibrary() {
        // ARRANGE: Use a dummy marker to prove the service is a pipe
        String[] dummyResult = {"ALIGNED_1", "ALIGNED_2"};
        when(smithWaterman.align(anyString(), anyString())).thenReturn(dummyResult);

        // ACT
        String[] result = alignmentService.smithWaterman("TIGER", "TAGGICR");

        // ASSERT: Prove the connection
        assertThat(result).isSameAs(dummyResult);
        verify(smithWaterman, times(1)).align("TIGER", "TAGGICR");
    }

    // --- Tests for NeedlemanWunsch (Instance Delegation) ---

    @Test
    void needlemanWunsch_delegateToLibrary() {
        // ARRANGE
        String[] dummyResult = {"GLOBAL_1", "GLOBAL_2"};
        when(needlemanWunsch.align(anyString(), anyString())).thenReturn(dummyResult);

        // ACT
        String[] result = alignmentService.needlemanWunsch("AGACTAR", "TGCAT");

        // ASSERT
        assertThat(result).isSameAs(dummyResult);
        verify(needlemanWunsch, times(1)).align("AGACTAR", "TGCAT");
    }

    // --- Tests for SeedAndExtend (Static Delegation) ---

    @Test
    void seedAndExtend_delegateToLibrary() {
        // We must use mockStatic because SeedAndExtend.align is a static method
        try (var mockedStatic = mockStatic(SeedAndExtend.class)) {
            // ARRANGE: Create a dummy array of Extensions
            Extension[] dummyExtensions = new Extension[1];
            // Note: If Extension has a private constructor, just use a null check or mock it

            mockedStatic.when(() -> SeedAndExtend.align(anyString(), anyString(), anyInt()))
                    .thenReturn(dummyExtensions);

            // ACT
            Extension[] result = alignmentService.seedAndExtend("SEQUENCEDATA", "DATABASES", 3);

            // ASSERT
            assertThat(result).isEqualTo(dummyExtensions);
            mockedStatic.verify(() -> SeedAndExtend.align("SEQUENCEDATA", "DATABASES", 3));
        }
    }
}