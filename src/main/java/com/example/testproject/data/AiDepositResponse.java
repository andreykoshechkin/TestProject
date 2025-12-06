package com.example.testproject.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiDepositResponse implements AiResponse {
    private String contentId;
    private boolean success;
    public boolean isSuccess() {
        return success;
    }
    public void isExistsSuccess(Consumer<AiDepositResponse> action) {
        if (isSuccess()) {
            action.accept(this); // this — это первый ответ
        }
    }
}
