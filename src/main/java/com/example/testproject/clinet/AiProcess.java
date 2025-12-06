package com.example.testproject.clinet;

import com.example.testproject.data.AiResponse;
import com.example.testproject.data.AiVerifyRequest;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface AiProcess <RES extends AiResponse, REQ extends  AiVerifyRequest> {

    Mono<RES> callFirstStage(REQ request) throws Exception;
    Mono<RES> callSecondStage(REQ request) throws Exception;
}
