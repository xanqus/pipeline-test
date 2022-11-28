package com.example.pipelinetest.dao;


import com.example.pipelinetest.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
