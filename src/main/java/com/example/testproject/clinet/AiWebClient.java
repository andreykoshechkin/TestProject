package com.example.testproject.clinet;

import com.example.testproject.data.AiResponse;
import com.example.testproject.data.AiVerifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiWebClient implements AiProcess<AiResponse, AiVerifyRequest> {

    private final WebClient webClient;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);

    @Override
    public Mono<AiResponse> callFirstStage(AiVerifyRequest request) {
        return webClient.post()
                .uri("/first-stage")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        resp -> Mono.error(new AiWebClientException("Ошибка API FirstStage: " + resp.statusCode())))
                .bodyToMono(AiResponse.class)
                .timeout(REQUEST_TIMEOUT)
                .onErrorMap(throwable -> new AiWebClientException("Сетевая ошибка или таймаут при FirstStage", throwable));
    }

    @Override
    public Mono<AiResponse> callSecondStage(AiVerifyRequest request) {
        return webClient.post()
                .uri("/second-stage")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        resp -> Mono.error(new AiWebClientException("Ошибка API SecondStage: " + resp.statusCode())))
                .bodyToMono(AiResponse.class)
                .timeout(REQUEST_TIMEOUT)
                .onErrorMap(throwable -> new AiWebClientException("Сетевая ошибка или таймаут при SecondStage", throwable));
    }
}