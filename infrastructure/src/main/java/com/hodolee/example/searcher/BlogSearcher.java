package com.hodolee.example.searcher;

import com.hodolee.example.searcher.dto.ApiResponseDto;

public interface BlogSearcher {

    ApiResponseDto searchBlog(final String query, final String sort, final Integer page);

}
