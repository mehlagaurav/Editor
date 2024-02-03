package com.reverb.editor.junit;

import com.reverb.editor.dao.entity.ArticleEntity;
import com.reverb.editor.domain.ArticleResource;
import com.reverb.editor.dto.ArticleDto;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EditorServiceBaseTest {

    public ArticleEntity articleEntity() {
        ArticleEntity article = ArticleEntity.builder().articleUUID("123456").author("xyz").
                description("test article").header("xyz").keywords("xyz").build();

        return article;
    }


    public ArticleDto articleDto() {
        ArticleDto article = ArticleDto.builder().author("xyz").
                description("test article").header("xyz").keywords("xyz").build();

        return article;
    }


    public ArticleResource articleResource(){
        ArticleResource article = ArticleResource.builder().author("xyz").
                description("test article").header("xyz").keywords("xyz").build();

        return article;
    }


    public List<ArticleEntity> articlesList() {
        List<ArticleEntity> articles = new ArrayList<>();
        ArticleEntity article1 = ArticleEntity.builder().articleUUID("123456").author("xyz").
                description("test article").header("xyz").keywords("xyz").build();
        ArticleEntity article2 = ArticleEntity.builder().articleUUID("85858").author("abc").
                description("abc").header("abc").keywords("abc").build();

        articles.add(article1);
        articles.add(article2);

        return articles;

    }

    public Pageable getPageableObject(){
        Pageable pageable= new Pageable() {
            @Override
            public int getPageNumber() {
                return 1;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        return pageable;
    }
}
