package com.hodolee.example.searcher;

import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.dto.ExternalApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "kakaoBlogClient", url = "https://dapi.kakao.com")
public interface KakaoBlogSearcher {

    @GetMapping("/v2/search/blog")
    ExternalApiResponseDto searchBlog(final BlogSearchDto blogSearchDto);

}