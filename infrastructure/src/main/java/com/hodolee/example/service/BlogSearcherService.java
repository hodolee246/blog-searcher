package com.hodolee.example.service;

import com.hodolee.example.dto.BlogSearchDto;
import com.hodolee.example.searcher.BlogSearcher;
import com.hodolee.example.searcher.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BlogSearcherService {

    private final BlogSearcher blogSearcher;

    public BlogSearcherService(@Qualifier("kakaoSearcher") BlogSearcher blogSearcher) {
        this.blogSearcher = blogSearcher;
    }

    public ApiResponseDto getBlog(BlogSearchDto blogSearchDto) {
        return blogSearcher.searchBlog(blogSearchDto.query(), blogSearchDto.sort(), blogSearchDto.page());
    }

}
