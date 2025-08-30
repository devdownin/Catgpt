package com.app.multillm.service;

import com.app.multillm.model.LLMResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class GeminiService {

    @Async
    public CompletableFuture<LLMResponse> query(String prompt) {
        try {
            // Simulate network latency
            Thread.sleep(800);
            LLMResponse response = new LLMResponse("Gemini Pro", "This is a mock response from Google.", 800, true, null);
            return CompletableFuture.completedFuture(response);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LLMResponse response = new LLMResponse("Gemini Pro", null, 0, false, e.getMessage());
            return CompletableFuture.completedFuture(response);
        }
    }
}
