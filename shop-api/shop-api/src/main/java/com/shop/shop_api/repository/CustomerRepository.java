package com.shop.shop_api.repository;

import com.shop.shop_api.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    @EntityGraph(attributePaths = "profile")
    List<Customer> findAll();

    @Override
    @EntityGraph(attributePaths = "profile")
    Page<Customer> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "profile")
    Optional<Customer> findById(Long id);
}
