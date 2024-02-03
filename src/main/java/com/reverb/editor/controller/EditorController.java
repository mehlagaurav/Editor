package com.reverb.editor.controller;

import com.reverb.editor.domain.ArticleResource;
import com.reverb.editor.dto.ArticleDto;
import com.reverb.editor.service.EditorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * APIs to VIEW, CREATE, UPDATE, LIST and DELETE ARTICLES in EDITOR APPLICATION
 *
 * @author Gaurav Mehla
 */
@CrossOrigin
@Slf4j
@Tag(name="Editor App")
@RestController
@RequestMapping(value = "/api/v1/articles", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EditorController {
    @Autowired
    private EditorServiceImpl articleService;

    @PostMapping
    @Operation(
            summary = "Create Article",
            description = "Creates new article in DB if not already present")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article created successfully"),
            @ApiResponse(responseCode = "401", description = "Article already exists")
    })
    public ResponseEntity<ArticleResource> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        log.debug("Request for creating article {}", articleDto.toString());
        ArticleResource articleResource = articleService.createArticle(articleDto);

        return new ResponseEntity<>(articleResource, HttpStatus.CREATED);

    }

    @PutMapping(value = "/{articleId}")
    @Operation(
            summary = "Update Article",
            description = "Updates Article in db with given ID")
    public ResponseEntity<ArticleResource> updateArticleById(@RequestBody ArticleDto createArticleDto,
                                                             @PathVariable String articleId, @RequestHeader final int ifMatch) {
        log.debug("Updating Article {}", createArticleDto.getHeader());
        ArticleResource articleResource = articleService.updateArticle(createArticleDto, articleId, ifMatch);
        return new ResponseEntity<>(articleResource, HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{articleId}", method = RequestMethod.DELETE)
    @Operation(summary="Delete Article")
    public void deleteArticleByArticleId(@PathVariable String articleId) {

        log.debug("Deleting Article with ID {}", articleId);
        articleService.deleteArticle(articleId);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{articleId}")
    @Operation(summary = "Get Article")
    public ResponseEntity<ArticleResource> getArticleById(
            @PathVariable @Valid
            String articleId) {

        log.debug("Get details of Article with id {}", articleId);
        ArticleResource articleResource = articleService.getArticleById(articleId);
        return ResponseEntity.ok(articleResource);
    }
@ResponseStatus(HttpStatus.OK)
@GetMapping
@Operation(summary = "Get All Articles")
public ResponseEntity<Page<ArticleResource>> getArticlesBySpec(
        @RequestParam(value = "author", required = false) final String author,
        @RequestParam(value = "keywords", required = false) final String keywords, final Pageable pageable) {

    log.debug("Get details of all Article");
    return ResponseEntity.ok(articleService.getArticles(author, keywords, pageable));

}
}
