package com.hodolee.example.api;

import com.hodolee.example.service.BlogSearcherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class ApiController {
    
    private final BlogSearcherService blogSearcherService;

    public ApiController(BlogSearcherService blogSearcherService) {
        this.blogSearcherService = blogSearcherService;
    }
    
    @GetMapping("/blog")
    public ResponseEntity<String> getBlog(@RequestParam("query") String query) {
        return ResponseEntity.ok(blogSearcherService.getNaverBlog(query));
    }
    
}
