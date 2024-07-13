package com.hodolee.example.service;

import com.hodolee.example.searcher.KakaoSearcher;
import com.hodolee.example.searcher.NaverSeacher;
import org.springframework.stereotype.Service;

@Service
public class BlogSearcherService {

    private final KakaoSearcher kakaoSearcher;
    private final NaverSeacher naverSearcher;

    public BlogSearcherService(KakaoSearcher kakaoSearcher,
                               NaverSeacher naverSearcher) {
        this.kakaoSearcher = kakaoSearcher;
        this.naverSearcher = naverSearcher;
    }

    public String getNaverBlog(String query) {
        return naverSearcher.getBlogData(query);
    }

    public String getKakaoBlog(String query) {
        return kakaoSearcher.getBlogData(query);
    }

}
