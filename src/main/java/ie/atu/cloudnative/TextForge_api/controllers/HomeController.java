package ie.atu.cloudnative.TextForge_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HomeController {
    @GetMapping("/")
    public String greet() {
        return "API to demonstrate TextForge.";
    }

    @GetMapping("/health")
    public String health() throws UnknownHostException {
        String hostname = InetAddress.getLocalHost().getHostName();
        return "Application is healths and running on: " + hostname +
                " (" + System.getProperty("os.name") + ")";
    }
}
