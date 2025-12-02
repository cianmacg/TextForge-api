package ie.atu.cloudnative.TextForge_api.controllers;

import ie.atu.cloudnative.TextForge_api.requests.VectorRequest;
import ie.atu.cloudnative.TextForge_api.services.VectorisationService;
import jakarta.validation.Valid;
import main.java.ie.atu.forge.Vectorisers.BagOfWords;
import main.java.ie.atu.forge.Vectorisers.TFIDF;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vector")
public class VectorisationController {
    private final VectorisationService vectorisationService;

    public VectorisationController(VectorisationService vectorisationService) {
        this.vectorisationService = vectorisationService;
    }

    @GetMapping("bagofwords")
    public BagOfWords bow(@Valid @RequestBody VectorRequest request) {
        return  vectorisationService.bow(request.documents());
    }

    @GetMapping("tfidf")
    public TFIDF tfidf(@Valid @RequestBody VectorRequest request) {
        return vectorisationService.tfidf(request.documents());
    }
}
