package com.hodolee.example.searcher;

import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.dto.ExternalApiResponse;

public interface BlogSearcher {

    ExternalApiResponse searchBlog(BlogSearchDto blogSearchDto);

}
