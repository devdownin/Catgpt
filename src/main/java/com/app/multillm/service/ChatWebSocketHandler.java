package com.app.multillm.service;

import com.app.multillm.dto.MultiLLMRequest;
import com.app.multillm.model.LLMResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LLMService llmService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocket connection established: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MultiLLMRequest request = objectMapper.readValue(message.getPayload(), MultiLLMRequest.class);
        log.info("Received request for prompt: '{}' for models: {}", request.getPrompt(), request.getModels());

        List<CompletableFuture<LLMResponse>> futures = llmService.queryModels(request);

        // Stream results as they complete
        futures.forEach(future ->
            future.thenAcceptAsync(response -> {
                try {
                    // Wrap the single response in a ComparisonResponse-like structure for consistency on the frontend
                    // Or just send the LLMResponse directly. Sending directly is simpler.
                    String jsonResponse = objectMapper.writeValueAsString(response);
                    session.sendMessage(new TextMessage(jsonResponse));
                } catch (Exception e) {
                    log.error("Error sending WebSocket message for single response", e);
                }
            })
        );

        // Save the conversation once all futures are complete
        llmService.saveConversationFromFutures(futures, request.getPrompt());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocket connection closed: {} with status: {}", session.getId(), status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket transport error for session: {}", session.getId(), exception);
    }
}
