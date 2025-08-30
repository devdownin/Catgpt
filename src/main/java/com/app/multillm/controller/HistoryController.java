package com.app.multillm.controller;

import com.app.multillm.model.Conversation;
import com.app.multillm.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public List<Conversation> getAllConversations() {
        return historyService.getAllConversations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conversation> getConversationById(@PathVariable String id) {
        return historyService.getConversationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
