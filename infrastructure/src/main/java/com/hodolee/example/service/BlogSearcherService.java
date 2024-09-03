package com.hodolee.example.service;

import com.hodolee.example.searcher.BlogSearcher;
import com.hodolee.example.searcher.dto.ApiResponseDto;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BlogSearcherService {

    private final BlogSearcher blogSearcher;

    public BlogSearcherService(@Qualifier("kakaoSearcher") BlogSearcher blogSearcher) {
        this.blogSearcher = blogSearcher;
    }

    public ApiResponseDto getBlog(String query, String sort, Integer page) {
        return blogSearcher.searchBlog(new BlogSearchDto(query, sort, page));
    }

}
