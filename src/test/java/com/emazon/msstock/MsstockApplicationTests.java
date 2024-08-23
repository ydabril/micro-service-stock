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
import static org.mockito.Mockito.verify;

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
		Category category = new Category(null, "Electronics", "Devices and gadgets");

		// Act
		categoryUseCase.saveCategory(category);

		// Assert
		verify(categoryPersistencePort).saveCategory(category);
	}
}
