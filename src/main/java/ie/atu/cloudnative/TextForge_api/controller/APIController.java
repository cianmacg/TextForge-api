package ie.atu.cloudnative.TextForge_api.controller;

import main.java.ie.atu.forge.Stemmers.Porter;
import main.java.ie.atu.forge.Tokenisers.Ngram;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class APIController {
    @PostMapping("/tokens")
    public String[] getTokens(@RequestBody String text){
        return Ngram.tokenise(text, 3);
    }

    @PostMapping("/stem")
    public String[] getStems(@RequestBody String text){
        return Porter.stem(text.split(" "));
    }
}