package ie.atu.cloudnative.TextForge_api.controllers;

import ie.atu.cloudnative.TextForge_api.requests.DecodeRequest;
import ie.atu.cloudnative.TextForge_api.requests.EncodeRequest;
import ie.atu.cloudnative.TextForge_api.requests.TokenRequest;
import ie.atu.cloudnative.TextForge_api.services.TokenisationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
public class TokenController {
    private final TokenisationService tokenisationService;

    public TokenController(TokenisationService tokenisationService) {
        this.tokenisationService = tokenisationService;
    }

    @GetMapping("/")
    public String home() {
        return "Available paths are: \n" +
                "/ngram\n" +
                "/shinge\n" +
                "/encode\n" +
                "/decode\n";
    }

    @GetMapping("/ngram")
    public String[] ngrams(@Valid @RequestBody TokenRequest request) {
        int window = request.window() != null && request.window() != 0 ? request.window() : 3;

        return tokenisationService.ngrams(request.text(), window);
    }

    @GetMapping("/shingle")
    public String[] shingles(@Valid @RequestBody TokenRequest request) {
        return tokenisationService.shingles(request.text(), request.window());
    }

    @GetMapping("/encode")
    public int[] encode(@Valid @RequestBody EncodeRequest request) {
        return tokenisationService.encode(request.text());
    }

    @GetMapping("/decode")
    public String decode(@Valid @RequestBody DecodeRequest request) {
        return tokenisationService.decode(request.tokens());
    }
}
