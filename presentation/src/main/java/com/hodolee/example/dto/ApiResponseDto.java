package com.hodolee.example.dto;

import java.util.HashMap;
import java.util.Map;

public class ApiResponseDto {

    private Map<String, Object> response = new HashMap<>();

    public Map<String, Object> getResponse() {
        return response;
    }

    public ApiResponseDto(Map<String, Object> response) {
        this.response = response;
    }

}
