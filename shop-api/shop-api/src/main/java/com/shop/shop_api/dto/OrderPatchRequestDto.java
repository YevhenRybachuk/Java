package com.shop.shop_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Fields that can be partially updated for an order")
public class OrderPatchRequestDto {

    @Schema(description = "Identifier of the customer who owns the order", example = "1")
    private Long customerId;

    @Schema(description = "Products included in the order")
    private List<OrderLineDto> items;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderLineDto> getItems() {
        return items;
    }

    public void setItems(List<OrderLineDto> items) {
        this.items = items;
    }
}
