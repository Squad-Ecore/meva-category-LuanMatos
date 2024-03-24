package com.meva.finance.dto;

import com.meva.finance.model.Category;
import lombok.Data;

@Data
public class CategoryDto {
    private Integer category;

    //Método que converte uma Category em CategoryDto
    public static CategoryDto fromCategory(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategory(category.getCategory());
        return categoryDto;
    }
}
