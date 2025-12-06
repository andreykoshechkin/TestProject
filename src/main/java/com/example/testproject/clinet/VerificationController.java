package com.example.testproject.clinet;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class VerificationController {

    private final AiVerificationService service;

    @PostMapping("/verify")
    public void verify(@RequestBody byte[] file) {
         service.processDocument2(file);
    }
}