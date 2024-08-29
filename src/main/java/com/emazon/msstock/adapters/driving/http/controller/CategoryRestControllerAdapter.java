package com.emazon.msstock.adapters.driving.http.controller;

import com.emazon.msstock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.msstock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.emazon.msstock.domain.api.ICategoryServicePort;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @PostMapping
    public ResponseEntity<Void> addCategory(@RequestBody AddCategoryRequest request) {
        categoryServicePort.saveCategory(categoryRequestMapper.addCategoryRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Pagination<CategoryResponse>> getAllCategories(@RequestParam Integer page,
                                                                         @RequestParam Integer size,
                                                                         @RequestParam(defaultValue = "asc") String sortDirection) {

        Pagination<Category> categories = categoryServicePort.getAllCategories(page, size, sortDirection);
        return ResponseEntity.ok(categoryResponseMapper.
                toPaginationResponse(categories));
    }
}
