package com.hodolee.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    Optional<SearchHistory> findByKeyword(String keyword);

}
