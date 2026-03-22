package apiGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
public class FallBackController {

    @GetMapping("/employeeFallback")
    public Mono<String> employeeServiceFallBack() {
        return Mono.just("Employee Service is currently unavailable. Please try again later.");
    }
    @RequestMapping(value = "/addressFallback")
    public Mono<Map<String, Object>> addressServiceFallBack() {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee service is down. Please try again later.");
        response.put("status", "SERVICE_UNAVAILABLE");
        response.put("timestamp", LocalDateTime.now());

        return Mono.just(response);
    }

}
