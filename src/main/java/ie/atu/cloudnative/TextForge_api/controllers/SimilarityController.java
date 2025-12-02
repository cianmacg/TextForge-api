package ie.atu.cloudnative.TextForge_api.controllers;

import ie.atu.cloudnative.TextForge_api.requests.SetSimilarityRequest;
import ie.atu.cloudnative.TextForge_api.requests.SimilarityRequest;
import ie.atu.cloudnative.TextForge_api.requests.TverskyRequest;
import ie.atu.cloudnative.TextForge_api.services.SimilarityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("similarity")
public class SimilarityController {
    private final SimilarityService similarityService;

    public SimilarityController(SimilarityService similarityService) {
        this.similarityService = similarityService;
    }

    @GetMapping("/cosine")
    public Double cosine(@Valid @RequestBody SimilarityRequest request) {
        return similarityService.cosine(request.v1(), request.v2());
    }

    @GetMapping("/jaccard")
    public Double jaccard(@Valid @RequestBody SetSimilarityRequest request) {
        return similarityService.jaccard(request.s1(), request.s2());
    }

    @GetMapping("/sorensen")
    public Double sorensen(@Valid @RequestBody SetSimilarityRequest request) {
        return similarityService.sorensen(request.s1(), request.s2());
    }

    @GetMapping("/tversky")
    public Double tversky(@Valid @RequestBody TverskyRequest request) {
        double a = request.a() != 0 ? request.a() : 0.75;
        double b = request.b() != 0 ? request.b() : 0.75;

        return similarityService.tversky(request.s1(), request.s2(), a, b);
    }

    @GetMapping({"/minhash", "/minhash/jaccard"})
    public Double minHashJaccard(@Valid @RequestBody SetSimilarityRequest request) {
        return similarityService.minHashJaccard(request.s1(), request.s2());
    }

    @GetMapping("/minhash/sorensen")
    public Double minHashSorensen(@Valid @RequestBody SetSimilarityRequest request) {
        return similarityService.minHashSorensen(request.s1(), request.s2());
    }
}
