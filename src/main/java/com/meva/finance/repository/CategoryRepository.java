package com.meva.finance.repository;

import com.meva.finance.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByDescriptionIgnoreCase(String description);
}
