package com.shop.shop_api.repository;

import com.shop.shop_api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(attributePaths = {"category", "tags"})
    List<Product> findAll();

    @Override
    @EntityGraph(attributePaths = {"category", "tags"})
    Optional<Product> findById(Long id);

    @EntityGraph(attributePaths = "category")
    @Query(
            value = "select p from Product p",
            countQuery = "select count(p) from Product p"
    )
    Page<Product> findPageWithCategory(Pageable pageable);

    @Query("""
            select distinct p
            from Product p
            join fetch p.category
            left join fetch p.tags
            """)
    List<Product> findAllOptimized();
}
