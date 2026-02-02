package com.nvminh162.b;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Service B đóng vai trò backend đơn giản, cố tình tạo lỗi/chậm
 * để Service A dùng Resilience4j xử lý.
 */
@RestController
@RequestMapping("/api/b")
public class ServiceBController {

    private final Random random = new Random();

    @GetMapping("/ok")
    public String ok() {
        return "B-OK " + LocalDateTime.now();
    }

    @GetMapping("/maybe-error")
    public String maybeError() {
        if (random.nextBoolean()) {
            throw new RuntimeException("B simulated error");
        }
        return "B-MAYBE " + LocalDateTime.now();
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        Thread.sleep(4000); // 4s để demo timeout/chậm
        return "B-SLOW " + LocalDateTime.now();
    }
}

