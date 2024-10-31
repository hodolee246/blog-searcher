package com.hodolee.example.service;

import com.hodolee.example.searcher.BlogSearcher;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.dto.ExternalApiResponse;
import com.hodolee.example.searcher.impl.KakaoSearcher;
import com.hodolee.example.searcher.impl.NaverSearcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class BlogSearcherServiceTest {

    @Mock
    private SearchHistoryService searchHistoryService;

    @Mock
    private BlogSearcher kakaoSearcher;

    @Mock
    private BlogSearcher naverSearcher;

    @InjectMocks
    private BlogSearcherService blogSearcherService;

    @Test
    @DisplayName("카카오 블로그 검색 실패 시 네이버 블로그 검색을 Fallback 처리하여 호출한다.")
    void kakaoFallbackNaver() {
        doThrow(new RuntimeException("예외 발생"))
                .when(kakaoSearcher).searchBlog(any(BlogSearchDto.class));

        ExternalApiResponse response = blogSearcherService.getKakaoBlog("테스트 쿼리", "accuracy", 1);

        Mockito.verify(naverSearcher).searchBlog(any(BlogSearchDto.class));
        assertThat(response).isNotNull();
    }

}