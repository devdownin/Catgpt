package com.app.multillm.service;

import com.app.multillm.model.LLMResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AnthropicService {

    @Async
    public CompletableFuture<LLMResponse> query(String prompt) {
        try {
            // Simulate network latency
            Thread.sleep(1200);
            LLMResponse response = new LLMResponse("Claude 3 Sonnet", "This is a mock response from Anthropic.", 1200, true, null);
            return CompletableFuture.completedFuture(response);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LLMResponse response = new LLMResponse("Claude 3 Sonnet", null, 0, false, e.getMessage());
            return CompletableFuture.completedFuture(response);
        }
    }
}
