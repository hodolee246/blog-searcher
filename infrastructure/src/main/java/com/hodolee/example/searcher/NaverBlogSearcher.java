package com.hodolee.example.searcher;

import com.hodolee.example.searcher.dto.ExternalApiResponseDto;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "naverBlogClient", url = "https://openapi.naver.com")
public interface NaverBlogSearcher {

    @GetMapping("/v1/search/blog.json")
    ExternalApiResponseDto searchBlog(final BlogSearchDto blogSearchDto);

}
