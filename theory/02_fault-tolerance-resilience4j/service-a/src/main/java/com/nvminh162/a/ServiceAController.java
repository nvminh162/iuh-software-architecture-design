package com.nvminh162.a;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/a")
public class ServiceAController {

    private final ServiceBClient serviceBClient;

    public ServiceAController(ServiceBClient serviceBClient) {
        this.serviceBClient = serviceBClient;
    }

    @GetMapping("/retry")
    public String retry() {
        return serviceBClient.callWithRetry();
    }

    @GetMapping("/cb")
    public String circuitBreaker() {
        return serviceBClient.callWithCircuitBreaker();
    }

    @GetMapping("/rate-limiter")
    public String rateLimiter() {
        return serviceBClient.callWithRateLimiter();
    }

    @GetMapping("/bulkhead")
    public String bulkhead() {
        return serviceBClient.callWithBulkhead();
    }
}

