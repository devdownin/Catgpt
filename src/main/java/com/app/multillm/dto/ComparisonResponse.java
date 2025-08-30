package com.app.multillm.dto;

import com.app.multillm.model.LLMResponse;
import lombok.Data;
import java.util.List;

@Data
public class ComparisonResponse {
    private String originalPrompt;
    private List<LLMResponse> responses;
}
