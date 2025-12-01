package ie.atu.cloudnative.TextForge_api.controllers;

import ie.atu.cloudnative.TextForge_api.requests.DecodeRequest;
import ie.atu.cloudnative.TextForge_api.requests.EncodeRequest;
import ie.atu.cloudnative.TextForge_api.requests.TokensRequest;
import ie.atu.cloudnative.TextForge_api.services.TokenisationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tokens")
public class TokensController {
    private final TokenisationService tokenisationService;

    public TokensController(TokenisationService tokenisationService) {
        this.tokenisationService = tokenisationService;
    }

    @GetMapping("/ngrams")
    public String[] ngrams(@RequestBody TokensRequest request) {
        return tokenisationService.ngrams(request.text(), request.window());
    }

    @GetMapping("/shingles")
    public String[] shingles(@RequestBody TokensRequest request) {
        return tokenisationService.shingles(request.text(), request.window());
    }

    @GetMapping("/encode")
    public int[] encode(@RequestBody EncodeRequest request) {
        return tokenisationService.encode(request.text());
    }

    @GetMapping("/decode")
    public String decode(@RequestBody DecodeRequest request) {
        return tokenisationService.decode(request.tokens());
    }
}
