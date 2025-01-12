package com.emazon.msstock.adapters.driven.jpa.mysql.repository;

import com.emazon.msstock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    Page<CategoryEntity> findAll(Pageable pageable);

    List<CategoryEntity> findAllById(Iterable<Long> categoryIds);
}
