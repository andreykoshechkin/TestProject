package com.example.testproject.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiDocumentVerificationRequest implements AiVerifyRequest {
    private String contentId;
}