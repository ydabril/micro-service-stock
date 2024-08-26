package com.emazon.msstock.adapters.driving.http.controller;

import com.emazon.msstock.adapters.driving.http.dto.request.AddBrandRequest;
import com.emazon.msstock.adapters.driving.http.dto.response.BrandResponse;
import com.emazon.msstock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.emazon.msstock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.emazon.msstock.domain.api.IBrandServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;

    @PostMapping
    public ResponseEntity<Void> addBrand(@RequestBody AddBrandRequest request) {
        brandServicePort.saveBrand(brandRequestMapper.addBrandRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
