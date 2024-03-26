package com.meva.finance.api;

import com.meva.finance.dto.CategoryDto;
import com.meva.finance.model.Category;
import com.meva.finance.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class CategoryControllerTest {
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryServiceMock;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.categoryController = new CategoryController(categoryServiceMock);
    }

    @Test
    void testGetCategoryByExtract_NotFound() {
        String description = "Joao e seu pé de feijão";

        Category category = new Category();
        category.setCategory(2);
        category.setDescription("NÃO_CATEGORIZADO");

        CategoryDto categoryDto = CategoryDto.fromCategory(category);

        // Mock behavior
        when(categoryServiceMock.getCategoryByExtract(description)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(categoryDto));

        // Call the method
        ResponseEntity<CategoryDto> responseEntity = categoryController.getCategoryByExtract(description);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(responseEntity.getBody(), categoryDto);
    }

}
