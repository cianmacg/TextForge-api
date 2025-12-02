package ie.atu.cloudnative.TextForge_api.controllers;

import ie.atu.cloudnative.TextForge_api.requests.AlignmentRequest;
import ie.atu.cloudnative.TextForge_api.requests.SeedAndExtendRequest;
import ie.atu.cloudnative.TextForge_api.services.AlignmentService;
import jakarta.validation.Valid;
import main.java.ie.atu.forge.Similarity.Alignment.Extension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("align")
public class AlignmentController {
    private final AlignmentService alignmentService;

    public AlignmentController(AlignmentService alignmentService) {
        this.alignmentService = alignmentService;
    }

    @GetMapping("/smithwaterman")
    public String[] smithWaterman(@Valid @RequestBody AlignmentRequest request) {
        return alignmentService.smithWaterman(request.s1(), request.s2());
    }

    @GetMapping("/needlemanwunsch")
    public String[] needlemanWunsch(@Valid @RequestBody AlignmentRequest request) {
        return alignmentService.needlemanWunsch(request.s1(), request.s2());
    }

    @GetMapping("/seedandextend")
    public Extension[] seedAndExtend(@Valid @RequestBody SeedAndExtendRequest request) {
        return alignmentService.seedAndExtend(request.s1(), request.s2(), request.kMerLength());
    }
}
