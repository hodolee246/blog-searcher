package com.hodolee.example.searcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class KakaoSearcher {

    private final String apiURL = "https://dapi.kakao.com/v2/search/blog?query=";

    @Value("kakao.key")
    private String apiKey;

    public String getBlogData(String query) {
        String url = apiURL + query;
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + apiKey);
        return get(url, headers);
    }

    private String get(String apiUrl, HttpHeaders headers) {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class).getBody();
    }

}
