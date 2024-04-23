package com.example.myboard.dto;

import com.example.myboard.entity.Article;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private Long id;

    private String title;

    private String content;

    public static ArticleDto fromOxEntity(Article article){
        return new ArticleDto(
                article.getId(),
                article.getTitle(),
                article.getContent()
        );
    }
//    public Article fromArticleDto(ArticleDto dto){
//        Article article = new Article();
//        article.setId(dto.getId());
//        article.setTitle(dto.getTitle());
//        article.setContent(dto.getContent());
//        return article;
//    }
}
