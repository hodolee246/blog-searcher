package com.hodolee.example.searcher;

import com.hodolee.example.searcher.dto.ApiResponseDto;
import com.hodolee.example.searcher.dto.BlogSearchDto;

public interface BlogSearcher {

    ApiResponseDto searchBlog(final BlogSearchDto blogSearchDto);

}
