package com.example.myboard.repository;

import com.example.myboard.entity.Article;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;

import java.util.List;
@SpringBootTest
@Transactional
//테스트용이 원본에 들어가지를 않기 위해 롤백 용도로 사용해줘야한다
//함수 단위에도 적용이 가능하다
@TestPropertySource(locations = "classpath:application-test.properties")
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;

    @Test
    void 모든데이터검색_클래스(){
        //Given
        Article article1 = new Article(1L,"가가가","111");
        Article article2 = new Article(2L,"나나나","222");
        Article article3 = new Article(3L,"다다다","333");
        // Long 타입은 뒤에 명시적으로 L을 붙여줘야한다
        List<Article> expectList = new ArrayList<>();
        expectList.add(article1);
        expectList.add(article2);
        expectList.add(article3);

        //When
        List<Article> resultLists = articleRepository.findAll();

        //Then
        assertThat(resultLists.toString()).isEqualTo(expectList.toString());

    }

    @Test
    void 모든데이터검색_클래스실패(){
        //Given
        Article article1 = new Article(1L,"라라라","111");
        Article article2 = new Article(2L,"나나나","222");
        Article article3 = new Article(3L,"다다다","333");
        // Long 타입은 뒤에 명시적으로 L을 붙여줘야한다
        List<Article> expectList = new ArrayList<>();
        expectList.add(article1);
        expectList.add(article2);
        expectList.add(article3);

        //When
        List<Article> resultLists = articleRepository.findAll();

        //Then
        assertThat(resultLists.toString()).isEqualTo(expectList.toString());
    }

    @Test
    void 전체데이터검색_개수(){
        //Given
        int expectCount = 3;

        //When
        int actualCount = articleRepository.findAll().size();

        //Then
        assertThat(actualCount).isEqualTo(expectCount);
    }

    @Test
    void 자료입력테스트_성공(){
        //Given
        Article expectarticle = new Article(4L,"라라라","444");

        //When
        Article newArticle = new Article(null,"라라라","444");
        articleRepository.save(newArticle);

        //Then
        Article acturalArticle = articleRepository.findById(4L).orElse(null);
        assertThat(acturalArticle.toString()).isEqualTo(expectarticle.toString());
    }

    @Test
    void 자료삭제_테스트_성공(){
        //Given
        Long deleteId = 4L;

        //When
        articleRepository.deleteById(4L);

        //Then
        Article actualResult = articleRepository.findById(4L).orElse(null);
        assertThat(actualResult).isEqualTo(null);
    }

    @Test
    void 자료수정_테스트(){
        //Given
        Article expectArticle = new Article(1L,"타이틀수정", "컨텐트수정");

        //When
        articleRepository.save(expectArticle);
        Article actualArticle = articleRepository.findById(1L).orElse(null);

        //Then
        assertThat(actualArticle.toString()).isEqualTo(expectArticle.toString());
    }
}