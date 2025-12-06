package com.example.testproject.clinet;

import com.example.testproject.data.AiDepositResponse;
import com.example.testproject.data.AiDocumentVerificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class VerificationRepository {

    public void saveFirstStage(AiDepositResponse response) {
        log.info("Сохранили первый этап в БД: {}", response);
    }

    public void saveSecondStage(AiDocumentVerificationResponse response) {
        log.info("Сохранили второй этап в БД: {}", response);
    }

    public void updateHistory(String message) {
        log.info("Обновили историю: {}", message);
    }
}