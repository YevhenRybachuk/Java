package com.shop.shop_api.repository;

import com.shop.shop_api.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Override
    @EntityGraph(attributePaths = {"order", "product"})
    List<OrderItem> findAll();

    @Override
    @EntityGraph(attributePaths = {"order", "product"})
    Page<OrderItem> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"order", "product"})
    Optional<OrderItem> findById(Long id);
}
