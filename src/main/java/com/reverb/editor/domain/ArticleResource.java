package com.reverb.editor.domain;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResource {
    private String articleUUID;
    private String header;
    private String description;
    private String text;
    private String keywords;
    private String author;
    private LocalDateTime publishDate;
    private int eTag;
}
