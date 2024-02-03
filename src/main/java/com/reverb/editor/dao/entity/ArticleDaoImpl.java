package com.reverb.editor.dao.entity;

import com.reverb.editor.constants.EditorConstants;
import com.reverb.editor.exceptions.EditorDaoException;
import com.reverb.editor.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class ArticleDaoImpl implements ArticleDao{

    @Autowired
    ArticleRepository articleRepository;
   @Override
    public ArticleEntity createArticle(ArticleEntity article) {
        try {
            log.debug("Creating new article with header {}", article.getHeader());
            return articleRepository.save(article);
        } catch (ObjectOptimisticLockingFailureException exception) {
            log.error(EditorConstants.ARTICLE_CREATION_FAIL_MSG, exception);
            throw new EditorDaoException(EditorConstants.ARTICLE_CREATION_FAIL_MSG, exception);
        } catch (RuntimeException exception) {
            log.error(EditorConstants.ARTICLE_CREATION_FAIL_MSG, exception);
            throw new EditorDaoException(EditorConstants.ARTICLE_CREATION_FAIL_MSG, exception);
        }

   }

    @Override
    public ArticleEntity updateArticle(ArticleEntity article) {
        try {
            log.debug("Updating article with header", article.getHeader());
            return articleRepository.save(article);
        } catch (ObjectOptimisticLockingFailureException ex) {
            log.error(EditorConstants.ARTICLE_UPDATION_FAIL_MSG, ex.getMostSpecificCause().getMessage());
            throw new EditorDaoException(EditorConstants.ALREADY_MODIFIED, ex);
        } catch (RuntimeException exception) {
            log.error(EditorConstants.ARTICLE_UPDATION_FAIL_MSG, exception);
            throw new EditorDaoException(EditorConstants.ARTICLE_UPDATION_FAIL_MSG, exception);
        }
    }

    @Override
    public List<ArticleEntity> getArticles() {
        log.debug("Fetching all the articles");
        return (List<ArticleEntity>) articleRepository.findAll();
    }

    @Override
    public ArticleEntity getArticleByUUID(String articleUUID) {
        log.debug("Get article by articleUUID", articleUUID);
        return articleRepository.findById(articleUUID).get();
    }

    @Override
    public void deleteArticle(ArticleEntity article) {
        try {
            log.debug("Deleting the article with articleUUID", article.getArticleUUID());
            articleRepository.delete(article);

        } catch (RuntimeException exception) {
            log.error(EditorConstants.ARTICLE_DELETION_FAIL_MSG, exception);
            throw new EditorDaoException(EditorConstants.ARTICLE_DELETION_FAIL_MSG, exception);
        }
    }

    @Override
    public ArticleEntity getArticleByHeaderAndAuthor(String header, String author) {
        log.debug("Get article with Header {} and written by Author {}", header, author);
        return articleRepository.getByHeaderAndAuthor(header, author);
    }

    @Override
    public List<ArticleEntity> getArticlesbySpec(Specification<ArticleEntity> spec) {
        log.debug("Fetching articles on the basis of provided specification {}", spec.toString());
        return articleRepository.findAll(spec);
    }
}
