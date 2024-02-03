package com.reverb.editor.util;

import com.reverb.editor.dao.entity.ArticleEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ArticleSpecification implements Specification<ArticleEntity> {

    private ArticleEntity articleFilter;
    public ArticleSpecification(ArticleEntity filter) {
        super();
        this.articleFilter = filter;
    }

    @Override
    public Predicate toPredicate(Root<ArticleEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.disjunction();
        List<Predicate> filters = new ArrayList<>();
        if (articleFilter.getAuthor() != null) {
            filters.add(criteriaBuilder.equal(root.get("author"), articleFilter.getAuthor()));
        }

        if (articleFilter.getKeywords() != null) {
            filters.add(criteriaBuilder.equal(root.get("keywords"), articleFilter.getKeywords()));
        }

        Predicate[] f = new Predicate[filters.size()];
        p.getExpressions().add(criteriaBuilder.and(filters.toArray(f)));
        return p;
    }
}
