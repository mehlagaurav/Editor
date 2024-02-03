package com.reverb.editor.util;

import com.reverb.editor.dao.entity.ArticleEntity;
import com.reverb.editor.dto.ArticleDto;

import java.util.UUID;

public class EditorUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
//    public static ArticleEntity getArticleEntity(Article article) {
//
//        ArticleEntity articleEntity = new ArticleEntity();
//        articleEntity.setArticleUUID(getUUID());
//        articleEntity.setAuthor(article.getAuthor());
//        articleEntity.setDescription(article.getDescription());
//        articleEntity.setHeader(article.getHeader());
//        articleEntity.setKeywords(article.getKeywords());
//        articleEntity.setPublishDate(article.getPublishDate());
//        articleEntity.setText(article.getText());
//
//        return null;
//    }
}
