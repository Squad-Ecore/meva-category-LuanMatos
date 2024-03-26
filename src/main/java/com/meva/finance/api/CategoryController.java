package com.meva.finance.api;

import com.meva.finance.dto.CategoryDto;
import com.meva.finance.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getCategoryByExtract/{description}")
    public ResponseEntity<CategoryDto> getCategoryByExtract(@PathVariable String description) {
        return categoryService.getCategoryByExtract(description);
    }
}
