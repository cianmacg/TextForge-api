package ie.atu.cloudnative.TextForge_api.controllers;

import ie.atu.cloudnative.TextForge_api.requests.MinkowskiRequest;
import ie.atu.cloudnative.TextForge_api.requests.SimilarityRequest;
import ie.atu.cloudnative.TextForge_api.services.DistanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("distance")
public class DistanceController {
    private final DistanceService distanceService;

    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @GetMapping("/cosine")
    public Double cosine(@Valid @RequestBody SimilarityRequest request) {
        return distanceService.cosine(request.v1(), request.v2());
    }

    @GetMapping("/canberra")
    public Double canberra(@Valid @RequestBody SimilarityRequest request) {
        return distanceService.canberra(request.v1(), request.v2());
    }

    @GetMapping("/chebyshev")
    public Double chebyshev(@Valid @RequestBody SimilarityRequest request) {
        return distanceService.chebyshev(request.v1(), request.v2());
    }

    @GetMapping("/euclidean")
    public Double euclidean(@Valid @RequestBody SimilarityRequest request) {
        return distanceService.euclidean(request.v1(), request.v2());
    }

    @GetMapping("/manhattan")
    public Double manhattan(@Valid @RequestBody SimilarityRequest request) {
        return distanceService.manhattan(request.v1(), request.v2());
    }

    @GetMapping("/minkowski")
    public Double minkowski(@Valid @RequestBody MinkowskiRequest request) {
        double p = request.p() != null ? request.p() : 0.5;

        return distanceService.minkowski(request.v1(), request.v2(), p);
    }
}
