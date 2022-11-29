package com.example.pipelinetest.controller;

import com.example.pipelinetest.dao.ArticleRepository;
import com.example.pipelinetest.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;

    @GetMapping("")
    public List<Article> getArticles() {


        return articleRepository.findAll();
    }

    @GetMapping("/test")
    public String test() {
        return "Test github webhook~~~~~~";
    }
}
