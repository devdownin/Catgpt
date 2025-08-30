package com.app.multillm.controller;

import com.app.multillm.dto.ComparisonResponse;
import com.app.multillm.dto.MultiLLMRequest;
import com.app.multillm.service.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final LLMService llmService;

    @PostMapping("/compare")
    public ComparisonResponse compare(@RequestBody MultiLLMRequest request) {
        return llmService.queryAndCompare(request);
    }
}
