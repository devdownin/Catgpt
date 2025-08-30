package com.app.multillm.service;

import com.app.multillm.dto.MultiLLMRequest;
import com.app.multillm.model.Conversation;
import com.app.multillm.model.LLMResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LLMService {

    private final OpenAIService openAIService;
    private final AnthropicService anthropicService;
    private final GeminiService geminiService;
    private final HistoryService historyService;

    public List<CompletableFuture<LLMResponse>> queryModels(MultiLLMRequest request) {
        List<CompletableFuture<LLMResponse>> futures = new ArrayList<>();
        List<String> models = request.getModels();
        String prompt = request.getPrompt();

        if (models == null || models.isEmpty()) {
            futures.add(openAIService.query(prompt));
            futures.add(anthropicService.query(prompt));
            futures.add(geminiService.query(prompt));
        } else {
            if (models.contains("openai")) {
                futures.add(openAIService.query(prompt));
            }
            if (models.contains("anthropic")) {
                futures.add(anthropicService.query(prompt));
            }
            if (models.contains("gemini")) {
                futures.add(geminiService.query(prompt));
            }
        }
        return futures;
    }

    public void saveConversationFromFutures(List<CompletableFuture<LLMResponse>> futures, String prompt) {
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRunAsync(() -> {
            List<LLMResponse> responses = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

            if (responses.stream().anyMatch(LLMResponse::isSuccess)) {
                Conversation conversation = new Conversation();
                conversation.setUserQuery(prompt);
                conversation.setTimestamp(LocalDateTime.now());
                Map<String, String> llmResponsesMap = responses.stream()
                    .filter(LLMResponse::isSuccess)
                    .collect(Collectors.toMap(LLMResponse::getModelName, LLMResponse::getContent));
                conversation.setLlmResponses(llmResponsesMap);
                historyService.saveConversation(conversation);
            }
        });
    }
}
