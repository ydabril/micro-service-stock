package com.emazon.msstock.infraestructure.configuration;

import com.emazon.msstock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.emazon.msstock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.msstock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.msstock.domain.api.ICategoryServicePort;
import com.emazon.msstock.domain.api.useCase.CategoryUseCase;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

}
