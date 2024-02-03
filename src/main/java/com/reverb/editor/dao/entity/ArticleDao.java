package com.reverb.editor.dao.entity;

import com.reverb.editor.exceptions.EditorDaoException;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {
    public ArticleEntity createArticle(ArticleEntity article) throws EditorDaoException;

    public ArticleEntity updateArticle(ArticleEntity article);

    public List<ArticleEntity> getArticles();

    public ArticleEntity getArticleByUUID(String articleUUID);

    public void deleteArticle(ArticleEntity article);

    public ArticleEntity getArticleByHeaderAndAuthor(String header, String author);

    List<ArticleEntity> getArticlesbySpec(Specification<ArticleEntity> spec);
}
