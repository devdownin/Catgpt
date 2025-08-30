package com.app.multillm.config;

import lombok.Data;

@Data
public class LLMProviderProperties {
    private String apiKey;
    private String model;
    private Integer maxTokens;
}
