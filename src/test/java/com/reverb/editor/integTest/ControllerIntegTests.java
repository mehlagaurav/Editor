package com.reverb.editor.integTest;

import com.reverb.editor.domain.ArticleResource;
import com.reverb.editor.dto.ArticleDto;
import com.reverb.editor.junit.EditorServiceBaseTest;
import com.reverb.editor.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerIntegTests {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeAll
    private static void init() {
        restTemplate = new RestTemplate();

    }

    @BeforeEach
    public void setup() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/articles");

    }
    @Test
    public void editorTest(){
        ArticleDto articleDto= ArticleDto.builder().author("xyz").
                description("test article").header("xyz").keywords("xyz").build();
        ResponseEntity<ArticleResource> response4Create=restTemplate.postForEntity(baseUrl.toString(),articleDto,ArticleResource.class);
        Assertions.assertEquals(response4Create.getBody().getAuthor(),articleDto.getAuthor());
        Assertions.assertEquals(1,articleRepository.count());

        String articleUUID=response4Create.getBody().getArticleUUID();
        String url4Get=baseUrl.concat("/").concat(articleUUID);
        ResponseEntity<ArticleResource> response4Get=restTemplate.getForEntity(url4Get,ArticleResource.class);
        Assertions.assertEquals(response4Get.getBody().getAuthor(),articleDto.getAuthor());

//        ArticleDto articleDto2= ArticleDto.builder().author("abc").
//                description("test article").header("abc").keywords("xyz").build();
//        ResponseEntity<ArticleResource> response4Create2=restTemplate.postForEntity(baseUrl,articleDto2,ArticleResource.class);
        String url4GetBySpec=baseUrl.concat("?keywords=abc&page=0&size=1&sort=string");
        //EditorServiceBaseTest baseTest=new EditorServiceBaseTest();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        ResponseEntity<Page<ArticleResource>> response4GetBySpec=restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Page<ArticleResource>>() {});

        Assertions.assertNotNull(response4GetBySpec.getBody());
    }

}
