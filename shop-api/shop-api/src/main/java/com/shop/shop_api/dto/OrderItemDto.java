package com.shop.shop_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Order item data used in API requests and responses")
public class OrderItemDto {

    @Schema(description = "Unique order item identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identifier of the order", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Order id is required")
    private Long orderId;

    @Schema(description = "Identifier of the ordered product", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Product id is required")
    private Long productId;

    @Schema(description = "Product name", example = "Laptop", accessMode = Schema.AccessMode.READ_ONLY)
    private String productName;

    @Schema(description = "Quantity of the product in the order", example = "2", minimum = "1")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
