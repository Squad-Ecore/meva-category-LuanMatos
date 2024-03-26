package com.meva.finance.service;

import com.meva.finance.dto.CategoryDto;
import com.meva.finance.model.Category;
import com.meva.finance.model.SubCategory;
import com.meva.finance.repository.CategoryRepository;
import com.meva.finance.repository.SubCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepositoryMock;

    @Mock
    private SubCategoryRepository subCategoryRepositoryMock;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.categoryService = new CategoryService(subCategoryRepositoryMock, categoryRepositoryMock);
    }

    @Test
    void testGetCategoryByExtractWhenSubCategoryIsPresent() {
        String description = "Alvaro e seu supermercado";

        Category category = new Category();
        category.setCategory(1);
        category.setDescription("Alimentação");

        SubCategory subCategory = new SubCategory();
        subCategory.setId(1);
        subCategory.setDescription("supermercado");
        subCategory.setCategory(category);

        // Mock behavior
        when(subCategoryRepositoryMock.findByDescriptionIgnoreCase("supermercado")).thenReturn(subCategory);

        // Call the method
        ResponseEntity<CategoryDto> responseEntity = categoryService.getCategoryByExtract(description);

        //Verifications
        verify(categoryRepositoryMock, never()).findByDescriptionIgnoreCase(category.getDescription());

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(responseEntity.getBody().getCategory(), category.getCategory());
    }

    @Test
    void testGetCategoryByExtractWhenSubCategoryIsNotPresent() {
        String description = "Joao e seu pé de feijão";

        Category category = new Category();
        category.setCategory(2);
        category.setDescription("NÃO_CATEGORIZADO");

        // Mock behavior
        when(subCategoryRepositoryMock.findByDescriptionIgnoreCase(description)).thenReturn(null);
        when(categoryRepositoryMock.findByDescriptionIgnoreCase("NÃO_CATEGORIZADO")).thenReturn(category);

        // Call the method
        ResponseEntity<CategoryDto> responseEntity = categoryService.getCategoryByExtract(description);

        //Verifications
        verify(categoryRepositoryMock, times(1)).findByDescriptionIgnoreCase(category.getDescription());

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertEquals(responseEntity.getBody().getCategory(), category.getCategory());
    }
}
