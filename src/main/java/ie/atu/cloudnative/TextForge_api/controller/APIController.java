package ie.atu.cloudnative.TextForge_api.controller;

import ie.atu.cloudnative.TextForge_api.services.*;
import ie.atu.cloudnative.TextForge_api.requests.*;
import main.java.ie.atu.forge.Similarity.Alignment.Extension;
import main.java.ie.atu.forge.Vectorisers.BagOfWords;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {
    private final TokenisationService tokenisationService;
    private final StemmingService stemmingService;
    private final VectorisationService vectorisationService;
    private final AlignmentService alignmentService;
    private final SimilarityService similarityService;

    public APIController(TokenisationService tokenisationService,
                         StemmingService stemmingService,
                         VectorisationService vectorisationService,
                         AlignmentService alignmentService,
                         SimilarityService similarityService) {

        this.tokenisationService = tokenisationService;
        this.stemmingService = stemmingService;
        this.vectorisationService = vectorisationService;
        this.alignmentService = alignmentService;
        this.similarityService = similarityService;
    }

    /*
    Spring won't let overloading methods with the same path mapping.
    As a result, it is necessary to create the 'TokensRequest' object.
     */
    @GetMapping("/tokens")
    public String[] tokenise(@RequestBody TokenRequest request){
        System.out.println("Request: " + request);

        String text = request.text();

        String method = request.method() != null ? request.method() : "default";
        int window = request.window() != null ? request.window() : 3;

        return tokenisationService.tokenise(text, method, window);
    }

    @GetMapping("/encode")
    public int[] encode(@RequestBody EncodeRequest request) {
        return tokenisationService.encode(request.text());
    }

    @GetMapping("/decode")
    public String decode(@RequestBody DecodeRequest request) {
        return tokenisationService.decode(request.tokens());
    }

    @GetMapping("/stem")
    public String[] stem(@RequestBody StemRequest request){
        return stemmingService.stem(request.text(), request.method());
    }

    @GetMapping("/vector")
    public BagOfWords vectorise(@RequestBody VectorRequest request){
        return vectorisationService.vectorise(request.words(), request.sentence());
    }

    @GetMapping("/similarity")
    public double similarity() {

    }

    @GetMapping("/align")
    public String[] align() {

    }

    @GetMapping("/seed")
    public Extension[] seedAndExtend() {

    }
}