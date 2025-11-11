package com.ogirafferes.lxp.catalog.domain.repository;

import com.ogirafferes.lxp.catalog.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
