package com.hodolee.example.searcher.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ExternalApiResponseDto {

    private final List<BlogDto> blogs = new ArrayList<>();
    @Setter
    private MetaData meta;

}
