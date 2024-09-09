package com.hodolee.example.searcher.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ExternalApiResponseDto {

    private Map<String, Object> response = new HashMap<>();

    public void injectionData(Object data) {
        this.response.put("response", data);
    }

}
