package com.reverb.editor.junit;

import com.reverb.editor.dao.entity.ArticleDao;
import com.reverb.editor.dao.entity.ArticleDaoImpl;
import com.reverb.editor.dto.ArticleDto;
import com.reverb.editor.exceptions.EditorDaoException;
import com.reverb.editor.exceptions.EditorServiceException;
import com.reverb.editor.exceptions.ResourceNotFoundException;
import com.reverb.editor.service.EditorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EditorServiceTest {
    private EditorServiceBaseTest daoData = new EditorServiceBaseTest();

    @Mock
    private ArticleDao articleDao = Mockito.mock(ArticleDaoImpl.class);

    @Mock
    private ArticleDto articleDto = Mockito.mock(ArticleDto.class);

    @InjectMocks
    private EditorServiceImpl editorService = new EditorServiceImpl();

    @Test
    public void createArticleTest() {
        Mockito.when(articleDao.getArticleByHeaderAndAuthor(Mockito.any(), Mockito.any())).thenReturn(null);
        Mockito.when(articleDao.createArticle(Mockito.any())).thenReturn(daoData.articleEntity());
        Assert.notNull(editorService.createArticle(articleDto), "Assertion Failed");
    }

    @Test
    public void createArticleWithDaoException() {
        Mockito.when(articleDao.getArticleByHeaderAndAuthor(Mockito.any(), Mockito.any()))
                .thenReturn(null);
        Mockito.when(articleDao.createArticle(Mockito.any())).thenThrow(new EditorDaoException());
        EditorServiceException exception = assertThrows(EditorServiceException.class, () -> editorService.createArticle(articleDto), "Assertion Failed");
        assertTrue(exception.getMessage().contains("Article could not be created"));
    }

    @Test
    public void createArticleWithDuplicateEntry() {
        Mockito.when(articleDao.getArticleByHeaderAndAuthor(Mockito.any(), Mockito.any()))
                .thenReturn(daoData.articleEntity());
        EditorServiceException exception = assertThrows(EditorServiceException.class, () -> editorService.createArticle(articleDto), "Assertion Failed");
        assertTrue(exception.getMessage().contains("Article you are trying to add already exists"));

    }

    @Test
    public void getArticlesByIdTest() {
        Mockito.when(articleDao.getArticleByUUID(Mockito.any()))
                .thenReturn(daoData.articleEntity());
        Assert.notNull(editorService.getArticleById("1234"), "Assertion Failed");
    }

    @Test
    public void getArticlesByIdForNonExistentArticleTest() {
        Mockito.when(articleDao.getArticleByUUID(Mockito.any()))
                .thenReturn(null);
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> editorService.getArticleById("1234"), "assertion failed");
        assertTrue(thrown.getMessage().contains("Article you specified does not exists"));
    }

    @Test
    public void updateArticleTest() {
        Mockito.when(articleDao.getArticleByUUID(Mockito.any()))
                .thenReturn(daoData.articleEntity());
        Mockito.when(articleDao.updateArticle(Mockito.any())).thenReturn(daoData.articleEntity());
        Assert.notNull(editorService.updateArticle(articleDto, "1234", 1), "Assertion Failed");

    }

    @Test
    public void updateArticleFailTest() {
        Mockito.when(articleDao.getArticleByUUID(Mockito.any()))
                .thenReturn(null);
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> editorService.updateArticle(articleDto, "1234", 1), "assertion failed");
        assertTrue(thrown.getMessage().contains("Article you specified does not exists"));
    }
}
