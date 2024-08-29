package com.emazon.msstock.adapters;

import com.emazon.msstock.adapters.driving.http.controller.CategoryRestControllerAdapter;
import com.emazon.msstock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.msstock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.emazon.msstock.domain.api.ICategoryServicePort;
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

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoryRestControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryServicePort categoryServicePort;

    @MockBean
    private ICategoryRequestMapper categoryRequestMapper;

    @MockBean
    private ICategoryResponseMapper categoryResponseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveCategory() throws Exception {
        AddCategoryRequest categoryRequest = new AddCategoryRequest("Category", "Description for category");
        Category category = new Category(null, "Category", "Description for category");

        when(categoryRequestMapper.addCategoryRequest(any(AddCategoryRequest.class))).thenReturn(category);
        doNothing().when(categoryServicePort).saveCategory(any(Category.class));

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated());

        verify(categoryRequestMapper).addCategoryRequest(any(AddCategoryRequest.class));
        verify(categoryServicePort).saveCategory(any(Category.class));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        Pagination<Category> pagination = new Pagination<>(
                List.of(),
                0,
                10,
                2,
                1,
                false,
                false
        );
        when(categoryServicePort.getAllCategories(anyInt(), anyInt(), anyString())).thenReturn(pagination);

        Pagination<CategoryResponse> paginationResponse = new Pagination<>(
                List.of(),
                0,
                10,
                2,
                1,
                false,
                false
        );
        when(categoryResponseMapper.toPaginationResponse(pagination)).thenReturn(paginationResponse);

        mockMvc.perform(get("/category")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}