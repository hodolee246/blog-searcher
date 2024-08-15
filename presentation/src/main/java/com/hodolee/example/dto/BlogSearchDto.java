package com.hodolee.example.dto;

public record BlogSearchDto(
        String query,
        Integer page,
        String sort
) {

    public BlogSearchDto {
        if (page == null) {
            page = 1;
        }

        if (sort == null) {
            sort = "accuracy";
        }
    }

}
