package com.shop.shop_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Product data used in API requests and responses")
public class ProductDto {

    @Schema(description = "Unique product identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Product name", example = "Laptop", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Product name is required")
    private String name;

    @Schema(description = "Product price", example = "1299.99", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0.01")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;

    @Schema(description = "Identifier of the product category", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Category id is required")
    private Long categoryId;

    @Schema(description = "Category name", example = "Electronics", accessMode = Schema.AccessMode.READ_ONLY)
    private String categoryName;

    @Schema(description = "Identifiers of tags assigned to the product", example = "[1, 2]")
    private List<Long> tagIds = new ArrayList<>();

    @Schema(description = "Names of tags assigned to the product", example = "[\"New\", \"Popular\"]", accessMode = Schema.AccessMode.READ_ONLY)
    private List<String> tagNames = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }
}
