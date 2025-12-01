package ie.atu.cloudnative.TextForge_api.controllers;

import ie.atu.cloudnative.TextForge_api.requests.MinkowskiRequest;
import ie.atu.cloudnative.TextForge_api.requests.SimilarityRequest;
import ie.atu.cloudnative.TextForge_api.services.DistanceService;
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
    public Double cosine(@RequestBody SimilarityRequest request) {
        return distanceService.cosine(request.v1(), request.v2());
    }

    @GetMapping("/canberra")
    public Double canberra(@RequestBody SimilarityRequest request) {
        return distanceService.canberra(request.v1(), request.v2());
    }

    @GetMapping("/chebyshev")
    public Double chebyshev(@RequestBody SimilarityRequest request) {
        return distanceService.chebyshev(request.v1(), request.v2());
    }

    @GetMapping("/euclidean")
    public Double euclidean(@RequestBody SimilarityRequest request) {
        return distanceService.euclidean(request.v1(), request.v2());
    }

    @GetMapping("/manhattan")
    public Double manhattan(@RequestBody SimilarityRequest request) {
        return distanceService.manhattan(request.v1(), request.v2());
    }

    @GetMapping("/minkowski")
    public Double minkowski(@RequestBody MinkowskiRequest request) {
        return distanceService.minkowski(request.v1(), request.v2(), request.p());
    }
}
