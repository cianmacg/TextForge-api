package ie.atu.cloudnative.TextForge_api.controller;

import ie.atu.cloudnative.TextForge_api.Services.StemmingService;
import ie.atu.cloudnative.TextForge_api.requests.DecodeRequest;
import ie.atu.cloudnative.TextForge_api.requests.EncodeRequest;
import ie.atu.cloudnative.TextForge_api.requests.StemRequest;
import ie.atu.cloudnative.TextForge_api.requests.TokensRequest;
import ie.atu.cloudnative.TextForge_api.Services.TokenisationService;
import main.java.ie.atu.forge.Stemmers.Porter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {
    private final TokenisationService tokenisationService;
    private final StemmingService stemmingService;

    public APIController(TokenisationService tokenisationService, StemmingService stemmingService) {
        this.tokenisationService = tokenisationService;
        this.stemmingService = stemmingService;
    }

    /*
    Spring won't let overloading methods with the same path mapping.
    As a result, it is necessary to create the 'TokensRequest' object.
     */
    @GetMapping("/tokens")
    public String[] getTokens(@RequestBody TokensRequest request){
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
    public String[] getStems(@RequestBody StemRequest request){
        return stemmingService.stem(request.text(), request.method());
    }
}