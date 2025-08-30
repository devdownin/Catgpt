package com.app.multillm.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userQuery;
    @ElementCollection
    private Map<String, String> llmResponses; // LLM -> Response
    private LocalDateTime timestamp;
    @ElementCollection
    private List<String> tags;
    private Integer rating;
    private String category;
}
