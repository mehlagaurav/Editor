package com.reverb.editor.repository;

import com.reverb.editor.dao.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends CrudRepository<ArticleEntity,String>, JpaSpecificationExecutor<ArticleEntity> {
public ArticleEntity getByHeaderAndAuthor(@Param("header") String header, @Param("author") String author);

Page<ArticleEntity> findAll(Pageable pageable);
}
