package com.hodolee.example.searcher.dto;

import java.util.HashMap;
import java.util.Map;

public class ApiResponseDto {

    private Map<String, Object> resultResponse = new HashMap<>();

    public void injectionData(Object data) {
        this.resultResponse.put("response", data);
    }

}
