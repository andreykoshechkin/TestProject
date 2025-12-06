package com.example.testproject.clinet;

import com.example.testproject.data.AiDepositResponse;
import com.example.testproject.data.AiDepositUploadRequest;
import com.example.testproject.data.AiDocumentVerificationRequest;
import com.example.testproject.data.AiDocumentVerificationResponse;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiVerificationService {

    private final AiWebClient aiWebClient;
    private final VerificationRepository repository;

    private static final String SUCCESS_FIRST_STAGE = "Первый этап верификации документов пройден";
    private static final String SUCCESS_SECOND_STAGE = "Второй этап верификации документов пройден";
    private static final String ERROR_MESSAGE = "При верификации документов произошла ошибка. Ошибка - %s";

    /*public void processDocument(byte[] content) {

        Try.run(() -> {

            log.info("Начало верификации документов через ИИ");

            aiWebClient.callFirstStage(buildDepositUploadRequest(content))
                    .cast(AiDepositResponse.class)
                    .doOnNext(response -> handleStageSuccess(response, repository::saveFirstStage, SUCCESS_FIRST_STAGE))
                    .block()
                    .isExistsSuccess(firstStageResponse -> {
                        aiWebClient.callSecondStage(buildRequestFoSecondStage(firstStageResponse))
                                .cast(AiDocumentVerificationResponse.class)
                                .doOnNext(response -> handleStageSuccess(response, repository::saveSecondStage, SUCCESS_SECOND_STAGE))
                                .block();
                    });

            log.info("Завершение верификации документов через ИИ");

        }).onFailure(AiWebClientException.class, exception -> {
            String errorMessage = String.format(ERROR_MESSAGE, exception.getMessage());
            repository.updateHistory(errorMessage);
            log.error("Ошибка при верификации документов через ИИ", exception);
        }).onFailure(Exception.class, exception -> {
            String errorMessage = String.format(ERROR_MESSAGE, exception.getMessage());
            repository.updateHistory(errorMessage);
            log.error("Непредвиденная ошибка при верификации документов через ИИ", exception);
        });
    }*/


    public void processDocument2(byte[] content) {
        Try.run(() -> {

            log.info("Начало верификации документов через ИИ");

            AiDepositResponse firstStageResponse = aiWebClient.callFirstStage(buildDepositUploadRequest(content))
                    .cast(AiDepositResponse.class)
                    .doOnNext(response -> handleStageSuccess(response, repository::saveFirstStage, SUCCESS_FIRST_STAGE))
                    .block();

            aiWebClient.callSecondStage(buildRequestFoSecondStage(firstStageResponse))
                    .cast(AiDocumentVerificationResponse.class)
                    .doOnNext(response -> handleStageSuccess(response, repository::saveSecondStage, SUCCESS_SECOND_STAGE))
                    .block();

            log.info("Завершение верификации документов через ИИ");

        }).onFailure(AiWebClientException.class, exception -> {
            String errorMessage = String.format(ERROR_MESSAGE, exception.getMessage());
            repository.updateHistory(errorMessage);
            log.error("Ошибка при верификации документов через ИИ - {}", exception.getMessage());
        }).onFailure(Exception.class, exception -> {
            String errorMessage = String.format(ERROR_MESSAGE, exception.getMessage());
            repository.updateHistory(errorMessage);
            log.error("Непредвиденная ошибка при верификации документов через ИИ. Ошибка - {}", exception.getMessage());
        });
    }

    private <T> void handleStageSuccess(T response, Consumer<T> saveFunction, String message) {
        log.info(message);
        saveFunction.accept(response);
        repository.updateHistory(message);
    }

    private AiDocumentVerificationRequest buildRequestFoSecondStage(AiDepositResponse response) {
        return new AiDocumentVerificationRequest(response.getContentId());
    }

    private AiDepositUploadRequest buildDepositUploadRequest(byte[] content) {
        return new AiDepositUploadRequest(Base64.getEncoder().encodeToString(content));
    }

}