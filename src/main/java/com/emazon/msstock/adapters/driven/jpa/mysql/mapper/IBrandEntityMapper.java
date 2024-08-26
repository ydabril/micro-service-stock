package com.emazon.msstock.adapters.driven.jpa.mysql.mapper;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.msstock.domain.model.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {
    Brand toModel(BrandEntity brandEntity);
    BrandEntity toEntity(Brand brand);
    List<Brand> toModelList(List<BrandEntity> brandEntities);
}
