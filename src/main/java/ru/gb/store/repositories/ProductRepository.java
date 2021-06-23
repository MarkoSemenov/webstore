package ru.gb.store.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategoryOrderByName(Pageable pageable, Category category);

    List<Product> findAllByNameContains(String name);

    Page<Product> findAllByPriceBetween(BigDecimal price, BigDecimal price2, Pageable pageable);

}
