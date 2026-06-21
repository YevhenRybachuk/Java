package com.shop.shop_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Fields that can be partially updated for an order item")
public class OrderItemPatchRequestDto {

    @Schema(description = "Identifier of the order", example = "1")
    private Long orderId;

    @Schema(description = "Identifier of the ordered product", example = "3")
    private Long productId;

    @Schema(description = "Quantity of the product in the order", example = "2", minimum = "1")
    private Integer quantity;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
