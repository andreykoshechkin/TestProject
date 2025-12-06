package com.example.testproject.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiDocumentVerificationResponse implements AiResponse {
    private String decision;
}