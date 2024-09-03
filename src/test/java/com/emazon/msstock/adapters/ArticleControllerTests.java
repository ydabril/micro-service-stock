package com.emazon.msstock.adapters;

import com.emazon.msstock.adapters.driving.http.controller.ArticleRestControllerAdapter;
import com.emazon.msstock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.ArticleResponse;
import com.emazon.msstock.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleResponseMapper;
import com.emazon.msstock.domain.api.IArticleServicePort;
import com.emazon.msstock.domain.model.Article;
import com.emazon.msstock.domain.model.Brand;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArticleRestControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
public class ArticleControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IArticleServicePort articleServicePort;

    @MockBean
    private IArticleRequestMapper iArticleRequestMapper;

    @MockBean
    private IArticleResponseMapper iArticleResponseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveArticleTest() throws Exception {
        AddArticleRequest articleRequest = new AddArticleRequest("name", new BigDecimal(10), Long.decode("1"), Long.decode("1"), new ArrayList<>());

        Article article = new Article(1L, "name", BigDecimal.ONE, Long.decode("1"), new Brand(1L, "name", "description"), new ArrayList<>() );

        when(iArticleRequestMapper.addArticleRequest(any(AddArticleRequest.class))).thenReturn(article);
        doNothing().when(articleServicePort).saveArticle(any(Article.class));

        mockMvc.perform(post("/article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleRequest))
        ).andExpect(status().isCreated());

        verify(iArticleRequestMapper).addArticleRequest(any(AddArticleRequest.class));
        verify(articleServicePort).saveArticle(any(Article.class));
    }

    @Test
    public void testControllerFetchPaginatedArticlesShouldReturnCorrectPageData() throws Exception {
        Pagination<Article> pagination = new Pagination<>(
                List.of(),
                0,
                10,
                2,
                1,
                false,
                false
        );
        when(articleServicePort.getAllArticles(anyInt(), anyInt(), anyString(), anyString())).thenReturn(pagination);

        Pagination<ArticleResponse> paginationResponse = new Pagination<>(
                List.of(),
                0,
                10,
                2,
                1,
                false,
                false
        );
        when(iArticleResponseMapper.toPaginationResponse(pagination)).thenReturn(paginationResponse);

        mockMvc.perform(get("/article")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "ASC")
                        .param("sortBy", "ARTICLE_NAME")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
