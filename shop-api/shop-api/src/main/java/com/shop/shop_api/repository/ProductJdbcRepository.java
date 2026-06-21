package com.shop.shop_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class ProductJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BigDecimal getAveragePrice() {
        BigDecimal average = jdbcTemplate.queryForObject(
                "select coalesce(avg(price), 0) from products",
                BigDecimal.class
        );
        return average == null ? BigDecimal.ZERO : average;
    }
}
