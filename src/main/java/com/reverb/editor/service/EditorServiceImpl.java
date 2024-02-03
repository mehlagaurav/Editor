package com.reverb.editor.service;

import com.reverb.editor.constants.EditorConstants;
import com.reverb.editor.dao.entity.ArticleDao;
import com.reverb.editor.dao.entity.ArticleEntity;
import com.reverb.editor.domain.ArticleResource;
import com.reverb.editor.dto.ArticleDto;
import com.reverb.editor.exceptions.EditorDaoException;
import com.reverb.editor.exceptions.EditorServiceException;
import com.reverb.editor.exceptions.ResourceNotFoundException;
import com.reverb.editor.util.ArticleSpecification;
import com.reverb.editor.util.EditorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class EditorServiceImpl implements EditorService{

    @Autowired
    private ArticleDao articleDao;

    @Override
    public ArticleResource createArticle(ArticleDto articleDto) {
        ArticleEntity articleEntity ;
        ArticleResource articleResource = new ArticleResource();
        try{
            articleEntity= articleDao.getArticleByHeaderAndAuthor(articleDto.getHeader(),articleDto.getAuthor());
            if(Objects.nonNull(articleEntity)){
                throw new EditorServiceException(HttpStatus.CONFLICT.value(),EditorConstants.DUPLICATE_ENTRY);

            }
            articleEntity = new ArticleEntity();
            BeanUtils.copyProperties(articleDto, articleEntity);
            articleEntity.setArticleUUID(EditorUtil.getUUID());
            log.debug("Creating new application with article UUID :{}", articleEntity.getArticleUUID());

            BeanUtils.copyProperties(articleDao.createArticle(articleEntity),articleResource);
            return articleResource;
        }
        catch (EditorDaoException e){
            log.error(EditorConstants.EDITORDAOEXCEPTION,e);
            throw new EditorServiceException(EditorConstants.INTERNAL_PROCESSING_ERROR);
        }

    }

    @Override
    public ArticleResource getArticleById(String articleUUID) {
        ArticleEntity articleEntity = articleDao.getArticleByUUID(articleUUID);
        if (articleEntity==null) {
            throw new ResourceNotFoundException(EditorConstants.ARTICLE_NOT_FOUND);
        }
        ArticleResource articleResource = new ArticleResource();
        BeanUtils.copyProperties(articleEntity,articleResource);
        return articleResource;
    }

    @Override
    public ArticleResource updateArticle(ArticleDto articleDto, String articleUUID, int ifMatch) {
        ArticleResource articleResource = new ArticleResource();
        try {
            ArticleEntity articleEntity = articleDao.getArticleByUUID(articleUUID);
            if (articleEntity==null) {
                throw new ResourceNotFoundException(EditorConstants.ARTICLE_NOT_FOUND);
            }
//            if(articleEntity.get().getETag() != ifMatch){
//                throw new EditorServiceException(EditorConstants.STALE_DATA);
//
//            }
            ArticleEntity newArticleEntity = new ArticleEntity();
            BeanUtils.copyProperties(articleDto, newArticleEntity);
            newArticleEntity.setArticleUUID(articleUUID);
            newArticleEntity.setETag(ifMatch);
            BeanUtils.copyProperties(articleDao.updateArticle(newArticleEntity),articleResource);
            return articleResource;

        } catch (EditorDaoException e) {
            log.error(EditorConstants.EDITORDAOEXCEPTION, e);
            throw new EditorServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteArticle(String articleUUID) {
        try {
            // 1. check whether article exists or not
            ArticleEntity articleEntity = articleDao.getArticleByUUID(articleUUID);
            if(articleEntity!=null) {
                log.debug(EditorConstants.DELETING_ARTICLE_WITH_HEADER, articleEntity.getHeader());
                articleDao.deleteArticle(articleEntity);
            }
        } catch (EditorDaoException e) {
            log.error(EditorConstants.EDITORDAOEXCEPTION, e);
            throw new EditorServiceException(e.getMessage());
        }
    }

    @Override
    public Page<ArticleResource> getArticles(String author, String keywords, Pageable pageable) {
        ArticleEntity filter = new ArticleEntity();
        filter.setAuthor(author);
        filter.setKeywords(keywords);
        Specification<ArticleEntity> spec = new ArticleSpecification(filter);
        List<ArticleEntity> articles = articleDao.getArticlesbySpec(spec);
        List<ArticleResource> articleResources = new ArrayList<>();
        if (articles == null) {
            throw new EditorServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    EditorConstants.UNABLE_TO_FETCH_ARTICLES);
        }

        articles.forEach(article -> {
            ArticleResource articleResource=new ArticleResource();
            BeanUtils.copyProperties(articleResource,article);
            articleResources.add(articleResource);
        });

        return new PageImpl<>(articleResources, pageable, articleResources.size());
    }
}
