package com.app.multillm.dto;

import lombok.Data;
import java.util.List;

@Data
public class MultiLLMRequest {
    private String prompt;
    private List<String> models; // e.g., ["openai", "claude", "gemini"]
}
