package com.example.myboard.service;

import com.example.myboard.dto.ArticleDto;
import com.example.myboard.entity.Article;
import com.example.myboard.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void insert(ArticleDto dto) {
//        Article article = dto.fromArticleDto(dto);
        Article article = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                        .build();
        articleRepository.save(article);
    }

    public List<ArticleDto> show() {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        return articleRepository.findAll()
                .stream()
                .map(x-> ArticleDto.fromOxEntity(x))
                .toList();
    }

    public ArticleDto findById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null){
            return null;
        } else
            return ArticleDto.fromOxEntity(articleRepository.findById(id).orElse(null));
    }

    public void updateArticle(ArticleDto dto) {
        Article article = Article.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public void update(ArticleDto articleDto) {

    }

    public Page<Article> pagingList(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
