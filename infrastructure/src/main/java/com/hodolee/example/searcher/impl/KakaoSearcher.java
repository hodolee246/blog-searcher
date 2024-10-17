package com.hodolee.example.searcher.impl;

import com.hodolee.example.searcher.KakaoBlogSearcher;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.dto.ExternalApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class KakaoSearcher {

    private final KakaoBlogSearcher kakaoBlogSearcher;

    public ExternalApiResponseDto searchBlog(final BlogSearchDto blogSearchDto) {
        return kakaoBlogSearcher.searchBlog(blogSearchDto.query(), blogSearchDto.sort(), blogSearchDto.page());
    }

}
