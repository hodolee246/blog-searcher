package com.hodolee.example.searcher.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolee.example.searcher.BlogSearcher;
import com.hodolee.example.searcher.dto.BlogDto;
import com.hodolee.example.searcher.dto.ExternalApiResponseDto;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import com.hodolee.example.searcher.dto.MetaData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class NaverSearcher implements BlogSearcher {

    @Value("${blog.search.naver.apiUri}")
    private String apiUri;

    @Value("${blog.search.naver.clientId}")
    private String clientId;

    @Value("${blog.search.naver.clientSecret}")
    private String clientSecret;

    public ExternalApiResponseDto searchBlog(final BlogSearchDto blogSearchDto) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(apiUri)
                .queryParam("query", blogSearchDto.query())
                .queryParam("sort", blogSearchDto.sort())
                .queryParam("page", blogSearchDto.page())
                .build();
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String apiResponse = get(uriComponents.toUri().toString(), requestHeaders);

        ExternalApiResponseDto response = new ExternalApiResponseDto();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(apiResponse);
            JsonNode items = jsonNode.get("items");
            if (items != null && items.isArray()) {
                for (JsonNode item : items) {
                    BlogDto blogDto = new BlogDto(
                            item.get("title").asText(),
                            item.get("description").asText(),
                            item.get("link").asText(),
                            item.get("bloggername").asText(),
                            item.get("postdate").asText()
                    );
                    response.getBlogs().add(blogDto);
                }
            }
            MetaData meta = new MetaData(
                    jsonNode.get("total").asInt(),
                    null,
                    null
            );
            response.setMeta(meta);
        } catch (IOException e) {
            throw new RuntimeException("네이버 API 응답 파싱 실패", e);
        }
        return response;
    }

    private String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

}
