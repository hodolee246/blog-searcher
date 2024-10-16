package com.hodolee.example.searcher.impl;

import com.hodolee.example.searcher.NaverBlogSearcher;
import com.hodolee.example.searcher.dto.ExternalApiResponseDto;
import com.hodolee.example.searcher.dto.BlogSearchDto;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Component
public class NaverSearcher {

    private final NaverBlogSearcher naverBlogSearcher;

    public ExternalApiResponseDto searchBlog(final BlogSearchDto blogSearchDto) {
        return naverBlogSearcher.searchBlog(blogSearchDto);
    }

}
