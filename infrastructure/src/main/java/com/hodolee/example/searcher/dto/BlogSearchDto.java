package com.hodolee.example.searcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogSearchDto {
    private String query;
    private String sort;
    private Integer page;

    public void convertNaverApi() {
        if ("accuracy".equals(sort)) {
            sort = "sim";
        } else {
            sort = "date";
        }
    }

}
