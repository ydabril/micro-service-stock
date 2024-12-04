package com.emazon.msstock.adapters;

import com.emazon.msstock.adapters.driving.http.controller.ArticleRestControllerAdapter;
import com.emazon.msstock.adapters.driving.http.dto.request.AddArticleRequest;
import com.emazon.msstock.adapters.driving.http.dto.request.AddSuppliesRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.ArticleResponse;
import com.emazon.msstock.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IArticleResponseMapper;
import com.emazon.msstock.domain.api.IArticleServicePort;
import com.emazon.msstock.domain.model.*;
import com.emazon.msstock.infraestructure.configuration.jwt.JwtAuthenticationFilter;
import com.emazon.msstock.infraestructure.configuration.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArticleRestControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArticleControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IArticleServicePort articleServicePort;

    @MockBean
    private IArticleRequestMapper iArticleRequestMapper;

    @MockBean
    private IArticleResponseMapper iArticleResponseMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveArticleTest() throws Exception {
        AddArticleRequest articleRequest = new AddArticleRequest(
                "name",
                "description",
                new BigDecimal(10),
                1L,
                1L,
                new ArrayList<>()
        );

        Article article = new Article(
                1L,
                "name",
                "description",
                BigDecimal.ONE,
                1L,
                new Brand(1L, "name", "description"),
                new ArrayList<>(),
                "image"
        );

        MockMultipartFile mockImage = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "imagen-falsa-contenido".getBytes()
        );

        MockMultipartFile mockArticleData = new MockMultipartFile(
                "articleData",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(articleRequest)
        );

        when(iArticleRequestMapper.addArticleRequest(any(AddArticleRequest.class))).thenReturn(article);
        doNothing().when(articleServicePort).saveArticle(any(Article.class), any(MultipartFile.class));

        mockMvc.perform(multipart("/article")
                .file(mockArticleData)
                .file(mockImage)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        ).andExpect(status().isCreated());
        
        verify(iArticleRequestMapper).addArticleRequest(any(AddArticleRequest.class));
        verify(articleServicePort).saveArticle(any(Article.class), any(MultipartFile.class));
    }

    @Test
    void addSuppliesControllerTest() throws Exception {
        AddSuppliesRequest addSuppliesRequest = new AddSuppliesRequest(1L, 10L);
        Supply supply = new Supply(1L, 10L);

        when(iArticleRequestMapper.addSupplyRequest(any(AddSuppliesRequest.class))).thenReturn(supply);
        doNothing().when(articleServicePort).addSupplies(any(Supply.class));

        mockMvc.perform(put("/article/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSuppliesRequest))
        ).andExpect(status().isCreated());

        verify(iArticleRequestMapper).addSupplyRequest(any(AddSuppliesRequest.class));
        verify(articleServicePort).addSupplies(any(Supply.class));
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

        mockMvc.perform(get("/article/all")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "ASC")
                        .param("sortBy", "ARTICLE_NAME")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
