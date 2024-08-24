package com.emazon.msstock;

import com.emazon.msstock.adapters.driving.http.controller.CategoryRestControllerAdapter;
import com.emazon.msstock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.emazon.msstock.domain.api.use_case.CategoryUseCase;
import com.emazon.msstock.domain.model.Category;
import com.emazon.msstock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

	// Test de la lógica de negocio
	@Test
	void saveCategory_shouldCallPersistencePort() {
		// Arrange
		Category category = new Category(null, "Toys", "Playthings for children");

		// Act
		categoryUseCase.saveCategory(category);

		// Assert
		verify(categoryPersistencePort).saveCategory(category);
	}

	@Test
	void getAllCategories_shouldReturnPagedAndSortedCategories() {
		// Arrange
		List<Category> mockCategories = List.of(
				new Category(1L, "Toys", "Playthings for children"),
				new Category(2L, "Books", "Books and literature")
		);

		Integer page = 0;
		Integer size = 10;
		String sortDirection = "ASC";

		when(categoryPersistencePort.getAllCategories(page, size, sortDirection)).thenReturn(mockCategories);

		// Act
		List<Category> result = categoryUseCase.getAllCategories(page, size, sortDirection);

		// Assert
		verify(categoryPersistencePort).getAllCategories(page, size, sortDirection);
		assertEquals(mockCategories, result);
	}
}
