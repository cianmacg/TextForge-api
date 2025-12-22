//package ie.atu.cloudnative.TextForge_api.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import ie.atu.cloudnative.TextForge_api.requests.AlignmentRequest;
//import ie.atu.cloudnative.TextForge_api.requests.SeedAndExtendRequest;
//import ie.atu.cloudnative.TextForge_api.services.AlignmentService;
//import main.java.ie.atu.forge.Similarity.Alignment.Extension;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AlignmentController.class)
//public class AlignmentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper; // To convert Java objects to JSON
//
//    @MockBean
//    private AlignmentService alignmentService; // Mock the service layer
//
//    @Test
//    void smithWaterman_shouldReturnAlignedStrings() throws Exception {
//        // ARRANGE
//        String[] mockResult = {"T-GGER", "TAGGER"};
//        AlignmentRequest request = new AlignmentRequest("TIGER", "TAGGER");
//
//        when(alignmentService.smithWaterman(anyString(), anyString())).thenReturn(mockResult);
//
//        // ACT & ASSERT
//        mockMvc.perform(get("/align/smithwaterman")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0]").value("T-GGER"))
//                .andExpect(jsonPath("$[1]").value("TAGGER"));
//    }
//
//    @Test
//    void needlemanWunsch_shouldReturnAlignedStrings() throws Exception {
//        // ARRANGE
//        String[] mockResult = {"GATTACA", "G-TT-CA"};
//        AlignmentRequest request = new AlignmentRequest("GATTACA", "GTTCA");
//
//        when(alignmentService.needlemanWunsch(anyString(), anyString())).thenReturn(mockResult);
//
//        // ACT & ASSERT
//        mockMvc.perform(get("/align/needlemanwunsch")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    void seedAndExtend_shouldReturnExtensions() throws Exception {
//        // ARRANGE
//        // Note: We return an empty array or dummy Extension objects
//        Extension[] mockResult = new Extension[0];
//        SeedAndExtendRequest request = new SeedAndExtendRequest("SEQ1", "SEQ2", 3);
//
//        when(alignmentService.seedAndExtend(anyString(), anyString(), anyInt())).thenReturn(mockResult);
//
//        // ACT & ASSERT
//        mockMvc.perform(get("/align/seedandextend")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray());
//    }
//}