package com.hodolee.example.dto;

public record SearchDto(
        String query,
        Integer page,
        String sort
) {

    public SearchDto {
        if (page == null) {
            page = 1;
        }

        if (sort == null) {
            sort = "accuracy";
        }
    }

}
