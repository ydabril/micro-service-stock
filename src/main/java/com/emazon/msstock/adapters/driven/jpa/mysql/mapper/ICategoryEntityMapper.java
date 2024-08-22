package com.emazon.msstock.adapters.driven.jpa.mysql.mapper;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.msstock.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryEntityMapper {
    Category toModel(CategoryEntity categoryEntity);
    CategoryEntity toEntity(Category category);
}
