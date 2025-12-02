package ie.atu.cloudnative.TextForge_api.controllers;

import ie.atu.cloudnative.TextForge_api.requests.AlignmentRequest;
import ie.atu.cloudnative.TextForge_api.requests.SeedAndExtendRequest;
import ie.atu.cloudnative.TextForge_api.services.AlignmentService;
import main.java.ie.atu.forge.Similarity.Alignment.Extension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlignmentController.class)
public class AlignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlignmentService alignmentService;

    private final String BASE_URL = "/align";

    // --- Smith-Waterman Tests (Testing @NotNull Validation) ---

    @Test
    void smithWaterman_shouldReturnOkAndAlignment_onValidRequest() throws Exception {
        // ARRANGE
        String s1 = "AGCT";
        String s2 = "AATT";
        String requestJson = String.format("{\"s1\": \"%s\", \"s2\": \"%s\"}", s1, s2);
        String[] mockResponse = {"AG-CT", "AA-TT"};

        when(alignmentService.smithWaterman(s1, s2)).thenReturn(mockResponse);

        // ACT & ASSERT
        mockMvc.perform(get(BASE_URL + "/smithwaterman")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("AG-CT"));

        verify(alignmentService, times(1)).smithWaterman(s1, s2);
    }

    @Test
    void smithWaterman_shouldReturnBadRequest_whenS1IsNull() throws Exception {
        // ARRANGE
        // Note: Testing for null explicitly, as @NotNull is the only constraint.
        String invalidJson = "{\"s1\": null, \"s2\": \"AATT\"}";

        // ACT & ASSERT
        mockMvc.perform(get(BASE_URL + "/smithwaterman")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(alignmentService);
    }

    // --- Needleman-Wunsch Tests (Testing @NotNull Validation) ---

    @Test
    void needlemanWunsch_shouldReturnBadRequest_whenS2IsNull() throws Exception {
        // ARRANGE
        String invalidJson = "{\"s1\": \"AGCT\", \"s2\": null}";

        // ACT & ASSERT
        mockMvc.perform(get(BASE_URL + "/needlemanwunsch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(alignmentService);
    }

    // --- Seed and Extend Tests (Testing Defaulting Logic) ---

    @Test
    void seedAndExtend_shouldCallServiceWithExplicitKMerLength() throws Exception {
        // ARRANGE
        String s1 = "ABC";
        String s2 = "BCA";
        int kMer = 5;
        String requestJson = String.format("{\"s1\": \"%s\", \"s2\": \"%s\", \"kMerLength\": %d}", s1, s2, kMer);

        // Mocking the return value of the service call
        when(alignmentService.seedAndExtend(any(), any(), anyInt())).thenReturn(new Extension[]{});

        // ACT
        mockMvc.perform(get(BASE_URL + "/seedandextend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        // ASSERT: Verify the service was called with the EXPLICIT value (5)
        verify(alignmentService, times(1)).seedAndExtend(eq(s1), eq(s2), eq(kMer));
    }

    @Test
    void seedAndExtend_shouldCallServiceWithDefaultKMerLength_whenKMerLengthIsNull() throws Exception {
        // ARRANGE
        String s1 = "ABC";
        String s2 = "BCA";
        // JSON omits kMerLength entirely or sets it to null
        String requestJson = String.format("{\"s1\": \"%s\", \"s2\": \"%s\"}", s1, s2);
        final int DEFAULT_K_MER = 3; // The default value from the controller logic

        // Mocking the return value of the service call
        when(alignmentService.seedAndExtend(any(), any(), anyInt())).thenReturn(new Extension[]{});

        // ACT
        mockMvc.perform(get(BASE_URL + "/seedandextend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        // ASSERT: Verify the service was called with the DEFAULT value (3)
        verify(alignmentService, times(1)).seedAndExtend(eq(s1), eq(s2), eq(DEFAULT_K_MER));
    }
}