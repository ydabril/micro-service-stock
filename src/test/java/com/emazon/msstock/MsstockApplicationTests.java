package com.emazon.msstock;

import com.emazon.msstock.adapters.driving.http.controller.CategoryRestControllerAdapter;
import com.emazon.msstock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.emazon.msstock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.msstock.domain.api.useCase.CategoryUseCase;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MsstockApplicationTests {

	@Mock
	private ICategoryPersistencePort categoryPersistencePort;

	@InjectMocks
	private CategoryUseCase categoryUseCase;

	@Mock
	private ICategoryRequestMapper categoryRequestMapper;

	@InjectMocks
	private CategoryRestControllerAdapter categoryRestControllerAdapter;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Test de contexto
	@Test
	void contextLoads() {
	}

	// Test de la lógica de negocio
	@Test
	void saveCategory_shouldCallPersistencePort() {
		// Arrange
		Category category = new Category(null, "Electronics", "Devices and gadgets");

		// Act
		categoryUseCase.saveCategory(category);

		// Assert
		verify(categoryPersistencePort).saveCategory(category);
	}

}
