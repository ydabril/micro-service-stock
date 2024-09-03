package com.emazon.msstock.adapters.driving.http.controller;

import com.emazon.msstock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.msstock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.emazon.msstock.domain.api.IBrandServicePort;
import com.emazon.msstock.domain.model.Brand;
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
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;

    @Operation(summary = "Create a new brand", description = "This endpoint allows you to create a new brand.")
    @ApiResponse(responseCode = "201", description = "Brand created correctly.", content = @Content)
    @ApiResponse(responseCode = "400", description = "Incorrect brand creation request", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
    @PostMapping
    public ResponseEntity<Void> addBrand(@RequestBody AddBrandRequest request) {
        brandServicePort.saveBrand(brandRequestMapper.addBrandRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "List all brands with pagination and sorting", description = "This endpoint allows you to list all availables brands. Query parameters can be used to control the pagination and order of the results.")
    @ApiResponse(responseCode = "200", description = "Correctly listed brands", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Pagination.class))})
    @ApiResponse(responseCode = "404", description = "No brands found for listing", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})
    @GetMapping
    public ResponseEntity<Pagination<BrandResponse>> getAllBrands(@RequestParam Integer page,
                                                                     @RequestParam Integer size,
                                                                     @RequestParam(defaultValue = "asc") String sortDirection) {

        Pagination<Brand> brands = brandServicePort.getAllBrands(page, size, sortDirection);
        return ResponseEntity.ok(brandResponseMapper.
                toPaginationResponse(brands));
    }
}
