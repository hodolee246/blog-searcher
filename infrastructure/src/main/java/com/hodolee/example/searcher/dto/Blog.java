package com.hodolee.example.searcher.dto;

import java.time.LocalDateTime;

public record Blog(String blogName,
                   String contents,
                   LocalDateTime dateTime,
                   String title,
                   String url) {


}
