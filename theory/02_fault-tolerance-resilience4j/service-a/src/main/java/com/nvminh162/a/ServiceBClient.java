package com.nvminh162.a;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceBClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ServiceBClient(RestTemplate restTemplate,
                          @Value("${service-b.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    /**
     * Demo Retry: gọi endpoint dễ lỗi, tự động thử lại nhiều lần.
     */
    @Retry(name = "serviceB", fallbackMethod = "retryFallback")
    public String callWithRetry() {
        return restTemplate.getForObject(baseUrl + "/maybe-error", String.class);
    }

    /**
     * Demo Circuit Breaker: nếu lỗi nhiều lần, mạch sẽ mở và chặn call.
     */
    @CircuitBreaker(name = "serviceB", fallbackMethod = "circuitBreakerFallback")
    public String callWithCircuitBreaker() {
        return restTemplate.getForObject(baseUrl + "/maybe-error", String.class);
    }

    /**
     * Demo Rate Limiter: giới hạn số request trong 1 khoảng thời gian.
     */
    @RateLimiter(name = "serviceB", fallbackMethod = "rateLimiterFallback")
    public String callWithRateLimiter() {
        return restTemplate.getForObject(baseUrl + "/ok", String.class);
    }

    /**
     * Demo Bulkhead: giới hạn số luồng đồng thời, tránh service A bị nghẽn.
     */
    @Bulkhead(name = "serviceB", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "bulkheadFallback")
    public String callWithBulkhead() {
        return restTemplate.getForObject(baseUrl + "/slow", String.class);
    }

    // -------- Fallbacks ----------

    private String retryFallback(Throwable ex) {
        return "Fallback RETRY từ A: " + ex.getClass().getSimpleName() + " - " + ex.getMessage();
    }

    private String circuitBreakerFallback(Throwable ex) {
        return "Fallback CIRCUIT BREAKER từ A: " + ex.getClass().getSimpleName() + " - " + ex.getMessage();
    }

    private String rateLimiterFallback(Throwable ex) {
        return "Fallback RATE LIMITER từ A (quá nhiều request): " + ex.getClass().getSimpleName();
    }

    private String bulkheadFallback(Throwable ex) {
        return "Fallback BULKHEAD từ A (hết slot xử lý): " + ex.getClass().getSimpleName();
    }
}

