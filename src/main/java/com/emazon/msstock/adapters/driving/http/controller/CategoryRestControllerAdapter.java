package com.emazon.msstock.adapters.driving.http.controller;

import com.emazon.msstock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.msstock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.msstock.domain.api.ICategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {
    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;

    @PostMapping
    public ResponseEntity<Void> addCategory(@RequestBody AddCategoryRequest request) {
        categoryServicePort.saveCategory(categoryRequestMapper.addCategoryRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
