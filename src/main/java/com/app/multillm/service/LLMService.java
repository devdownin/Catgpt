package com.app.multillm.service;

import com.app.multillm.dto.ComparisonResponse;
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

    public ComparisonResponse queryAndCompare(MultiLLMRequest request) {
        List<CompletableFuture<LLMResponse>> futures = new ArrayList<>();

        List<String> models = request.getModels();
        if (models == null || models.isEmpty() || models.contains("openai")) {
            futures.add(openAIService.query(request.getPrompt()));
        }
        if (models == null || models.isEmpty() || models.contains("anthropic")) {
            futures.add(anthropicService.query(request.getPrompt()));
        }
        if (models == null || models.isEmpty() || models.contains("gemini")) {
            futures.add(geminiService.query(request.getPrompt()));
        }


        // Wait for all futures to complete
        List<LLMResponse> responses = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        // Create and save conversation if there is at least one successful response
        if (responses.stream().anyMatch(LLMResponse::isSuccess)) {
            Conversation conversation = new Conversation();
            conversation.setUserQuery(request.getPrompt());
            conversation.setTimestamp(LocalDateTime.now());
            Map<String, String> llmResponsesMap = responses.stream()
                .filter(LLMResponse::isSuccess)
                .collect(Collectors.toMap(LLMResponse::getModelName, LLMResponse::getContent));
            conversation.setLlmResponses(llmResponsesMap);
            historyService.saveConversation(conversation);
        }

        ComparisonResponse comparisonResponse = new ComparisonResponse();
        comparisonResponse.setOriginalPrompt(request.getPrompt());
        comparisonResponse.setResponses(responses);

        return comparisonResponse;
    }
}
