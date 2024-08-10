package com.hodolee.example.service;

import com.hodolee.example.searcher.KakaoSearcher;
import com.hodolee.example.searcher.NaverSeacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogSearcherService {

    private final KakaoSearcher kakaoSearcher;
    private final NaverSeacher naverSearcher;

    public String getNaverBlog(String query) {
        return naverSearcher.getBlogData(query);
    }

    public String getKakaoBlog(String query) {
        return kakaoSearcher.getBlogData(query);
    }

}
