package com.reverb.editor.service;

import com.reverb.editor.domain.ArticleResource;
import com.reverb.editor.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface EditorService {

    public ArticleResource createArticle(ArticleDto articleDto);

    public ArticleResource getArticleById(String articleUUID);

    public ArticleResource updateArticle(ArticleDto articleDto, String articleUUID, int ifMatch);

    public void deleteArticle(String articleUUID);

    public Page getArticles(String author, String keywords, Pageable pageable);

}
