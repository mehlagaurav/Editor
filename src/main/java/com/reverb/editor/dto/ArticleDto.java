package com.reverb.editor.dto;

import com.reverb.editor.constants.EditorConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    @Size(message = EditorConstants.HEADER_CONSTRAINT_MESSAGE, max = EditorConstants.MAX_HEADER_SIZE_ALLOWED)
    @NotEmpty(message = EditorConstants.HEADER_EMPTY_MESSAGE)
    private String header;
    @Size(max = EditorConstants.MAX_DESCRIPTION_SIZE_ALLOWED)
    private String description;
    private String text;
    private String keywords;
    private String author;
    private LocalDateTime publishDate;

}
