package com.app.multillm.service;

import com.app.multillm.model.Conversation;
import com.app.multillm.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final ConversationRepository conversationRepository;

    public Conversation saveConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    public Optional<Conversation> getConversationById(String id) {
        return conversationRepository.findById(id);
    }

    public void deleteConversation(String id) {
        conversationRepository.deleteById(id);
    }
}
