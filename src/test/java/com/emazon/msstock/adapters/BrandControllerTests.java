package com.emazon.msstock.adapters;

import com.emazon.msstock.adapters.driving.http.controller.BrandRestControllerAdapter;
import com.emazon.msstock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.msstock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.emazon.msstock.domain.api.IBrandServicePort;
import com.emazon.msstock.domain.model.Brand;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BrandRestControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
public class BrandControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBrandServicePort brandServicePort;

    @MockBean
    private IBrandRequestMapper brandRequestMapper;

    @MockBean
    private IBrandResponseMapper brandResponseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveBrand() throws Exception {
        AddBrandRequest brandRequest = new AddBrandRequest("Brand", "Description for brand");
        Brand brand = new Brand(null, "Brand", "Description for brand");

        when(brandRequestMapper.addBrandRequest(any(AddBrandRequest.class))).thenReturn(brand);
        doNothing().when(brandServicePort).saveBrand(any(Brand.class));

        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isCreated());

        verify(brandRequestMapper).addBrandRequest(any(AddBrandRequest.class));
        verify(brandServicePort).saveBrand(any(Brand.class));
    }

    @Test
    public void testGetAllBrand() throws Exception {
        Pagination<Brand> pagination = new Pagination<>(
                List.of(),
                0,
                10,
                2,
                1,
                false,
                false
        );
        when(brandServicePort.getAllBrands(anyInt(), anyInt(), anyString())).thenReturn(pagination);

        Pagination<BrandResponse> paginationResponse = new Pagination<>(
                List.of(),
                0,
                10,
                2,
                1,
                false,
                false
        );
        when(brandResponseMapper.toPaginationResponse(pagination)).thenReturn(paginationResponse);

        mockMvc.perform(get("/brand")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
