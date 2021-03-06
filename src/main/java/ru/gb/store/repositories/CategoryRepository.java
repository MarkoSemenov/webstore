package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.store.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByName(String name);

    Category findCategoryById(Long id);

}
