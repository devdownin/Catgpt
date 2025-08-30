package com.app.multillm.service;

import com.app.multillm.model.LLMResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OpenAIService {

    @Async
    public CompletableFuture<LLMResponse> query(String prompt) {
        try {
            // Simulate network latency
            Thread.sleep(1000);
            String markdownContent = "Here is a list:\n\n*   Item 1\n*   Item 2\n\nAnd here is a code block:\n\n```java\npublic class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, Markdown!\");\n    }\n}\n```";
            LLMResponse response = new LLMResponse("GPT-4", markdownContent, 1000, true, null);
            return CompletableFuture.completedFuture(response);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LLMResponse response = new LLMResponse("GPT-4", null, 0, false, e.getMessage());
            return CompletableFuture.completedFuture(response);
        }
    }
}
