package com.hodolee.example.searcher;

import com.hodolee.example.searcher.dto.ExternalApiResponseDto;
import com.hodolee.example.searcher.dto.BlogSearchDto;

public interface BlogSearcher {

    ExternalApiResponseDto searchBlog(final BlogSearchDto blogSearchDto);

}
