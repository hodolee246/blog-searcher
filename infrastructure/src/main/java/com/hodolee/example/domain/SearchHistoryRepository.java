package com.hodolee.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    Optional<SearchHistory> findByKeyword(String keyword);

    @Query("SELECT s.keyword FROM SearchHistory s ORDER BY s.count DESC LIMIT :limit")
    List<String> findTopKeyword(@Param("limit") int limit);
}
