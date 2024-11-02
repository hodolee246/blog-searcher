package com.hodolee.example.api;

import com.hodolee.example.dto.SearchDto;
import com.hodolee.example.searcher.dto.ExternalApiResponse;
import com.hodolee.example.searcher.dto.kakao.KakaoApiResponseDto;
import com.hodolee.example.searcher.dto.kakao.KakaoInfo;
import com.hodolee.example.service.BlogSearcherService;
import com.hodolee.example.service.SearchHistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @MockBean
    BlogSearcherService blogSearcherService;
    @MockBean
    SearchHistoryService searchHistoryService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("카카오 블로그 검색 테스트")
    void kakaoBlogTest() throws Exception {
        SearchDto searchDto = new SearchDto("query", 1, "accuracy");

        KakaoInfo kakaoInfo = KakaoInfo.builder()
                .build();
        KakaoApiResponseDto kakaoApiResponseDto = KakaoApiResponseDto
                .builder()
                .documents(Arrays.asList(kakaoInfo))
                .build();
        ExternalApiResponse expectedResponse = new ExternalApiResponse(kakaoApiResponseDto, null);

        when(blogSearcherService.getKakaoBlog(searchDto.query(), searchDto.sort(), searchDto.page()))
                .thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/blog")
                        .param("query", searchDto.query())
                        .param("sort", searchDto.sort())
                        .param("page", String.valueOf(searchDto.page())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kakaoApiResponseDto").isNotEmpty())
                .andExpect(jsonPath("$.naverBlogResponseDto").isEmpty());
    }

}