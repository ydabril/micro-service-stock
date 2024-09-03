package com.emazon.msstock.adapters.driving.http.controller;

import com.emazon.msstock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.CategoryResponse;
import com.emazon.msstock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.emazon.msstock.domain.api.ICategoryServicePort;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.model.Pagination;
import com.emazon.msstock.infraestructure.exception_handler.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Create a category", description = "This endpoint allows you to create a new category")
    @ApiResponse(responseCode = "201", description = "Category created correctly", content = @Content)
    @ApiResponse(responseCode = "400", description = "Incorrect category creation request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
    @PostMapping
    public ResponseEntity<Void> addCategory(@RequestBody AddCategoryRequest request) {
        categoryServicePort.saveCategory(categoryRequestMapper.addCategoryRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "List all categories with pagination and sorting", description = "This endpoint allows you to list all available categories. Query parameters can be used to control the pagination and order of the results.")
    @ApiResponse(responseCode = "200", description = "Correctly listed categories", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Pagination.class))})
    @ApiResponse(responseCode = "404", description = "No categories found for listing", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
    @GetMapping
    public ResponseEntity<Pagination<CategoryResponse>> getAllCategories(@RequestParam Integer page,
                                                                         @RequestParam Integer size,
                                                                         @RequestParam(defaultValue = "asc") String sortDirection) {

        Pagination<Category> categories = categoryServicePort.getAllCategories(page, size, sortDirection);
        return ResponseEntity.ok(categoryResponseMapper.
                toPaginationResponse(categories));
    }
}
