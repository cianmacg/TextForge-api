package ie.atu.cloudnative.TextForge_api.controller;

import ie.atu.cloudnative.TextForge_api.requests.DecodeRequest;
import ie.atu.cloudnative.TextForge_api.requests.EncodeRequest;
import ie.atu.cloudnative.TextForge_api.requests.TokensRequest;
import ie.atu.cloudnative.TextForge_api.Services.TokenisationService;
import main.java.ie.atu.forge.Stemmers.Porter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {
    private final TokenisationService tokenisationService;

    public APIController(TokenisationService tokenisationService) {
        this.tokenisationService = tokenisationService;
    }

    /*
    Spring won't let overloading methods with the same path mapping.
    As a result, it is necessary to create the 'TokensRequest' object.
     */
    @PostMapping("/tokens")
    public String[] getTokens(@RequestBody TokensRequest request){
        String text = request.text();

        String method = request.method() != null ? request.method() : "default";
        int window = request.window() != null ? request.window() : 3;

        return tokenisationService.tokenise(text, method, window);
    }

    @PostMapping("/encode")
    public int[] encode(@RequestBody EncodeRequest request) {
        return tokenisationService.encode(request.text());
    }

    @PostMapping("/decode")
    public String decode(@RequestBody DecodeRequest request) {
        return tokenisationService.decode(request.tokens());
    }

    @PostMapping("/stem")
    public String[] getStems(@RequestBody String text){
        return Porter.stem(text.split(" "));
    }
}