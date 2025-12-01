package ie.atu.cloudnative.TextForge_api.controllers;

import ie.atu.cloudnative.TextForge_api.requests.StemRequest;
import ie.atu.cloudnative.TextForge_api.services.StemmingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stem")
public class StemmingController {
    private final StemmingService stemmingService;

    public StemmingController(StemmingService stemmingService) {
        this.stemmingService = stemmingService;
    }

    @GetMapping("/porter")
    public String[] porter(@RequestBody StemRequest request) {
        return stemmingService.porter(request.text());
    }

    @GetMapping("/lancaster")
    public String[] lancaster(@RequestBody StemRequest request) {
        return stemmingService.lancaster(request.text());
    }

    @GetMapping("/lovins")
    public String[] lovins(@RequestBody StemRequest request) {
        return stemmingService.lovins(request.text());
    }
}
