package com.meva.finance.service;

import com.meva.finance.dto.CategoryDto;
import com.meva.finance.model.Category;
import com.meva.finance.model.SubCategory;
import com.meva.finance.repository.CategoryRepository;
import com.meva.finance.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private SubCategoryRepository subCategoryRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<CategoryDto> getCategoryByExtract(String description) {
        // Remover palavras com menos de 3 letras
        description = removeSmallWords(description);

        // Separar as palavras restantes
        String[] palavras = separateWords(description);

        // Pesquisar cada palavra no banco de dados
        for (String palavra : palavras) {
            Optional<SubCategory> optionalSubCategory = Optional.ofNullable(subCategoryRepository.findByDescriptionIgnoreCase(palavra));

            if (optionalSubCategory.isPresent()) {
                CategoryDto categoryDto = CategoryDto.fromCategory(optionalSubCategory.get().getCategory());
                return ResponseEntity.ok(categoryDto);
            }
        }

        Category category = categoryRepository.findByDescriptionIgnoreCase("NÃO_CATEGORIZADO");
        CategoryDto categoryDto = CategoryDto.fromCategory(category);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categoryDto);
    }

    //Método responsável por remover palavras com menos de 3 letras
    private String removeSmallWords(String texto) {
        return texto.replaceAll("\\b\\w{1,3}\\b", "");
    }

    //Método responsável por separar as palavras
    private String[] separateWords(String texto) {
        return texto.split("\\s+");
    }
}
