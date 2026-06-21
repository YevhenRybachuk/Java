package com.shop.shop_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Fields that can be partially updated for a product")
public class ProductPatchRequestDto {

    @Schema(description = "Product name", example = "Laptop")
    private String name;

    @Schema(description = "Product price", example = "1299.99", minimum = "0.01")
    private BigDecimal price;

    @Schema(description = "Identifier of the product category", example = "2")
    private Long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
