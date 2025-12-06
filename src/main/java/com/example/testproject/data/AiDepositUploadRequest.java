package com.example.testproject.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiDepositUploadRequest implements AiVerifyRequest {
    private String documentBase64;
}