package com.reverb.editor.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Article", uniqueConstraints = {@UniqueConstraint(columnNames = {"HEADER", "AUTHOR"})})
public class ArticleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @UuidGenerator
    @Column(name = "articleUUID")
    private String articleUUID;

    @Column(name = "HEADER")
    private String header;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TEXT", columnDefinition = "TEXT")
    private String text;

    @Column(name = "KEYWORDS")
    private String keywords;

    @Column(name = "PUBLISH_DATE")
    private LocalDateTime publishDate;

    @Column(name = "AUTHOR")
    private String author;

    @Version
    @Column(name = "Etag")
    private int eTag;

}
