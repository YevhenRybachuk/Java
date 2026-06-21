package com.shop.shop_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Order data with customer and ordered products")
public class OrderDto {

    @Schema(description = "Unique order identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identifier of the customer who owns the order", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Customer id is required")
    private Long customerId;

    @Schema(description = "Customer name", example = "John Smith", accessMode = Schema.AccessMode.READ_ONLY)
    private String customerName;

    @Schema(description = "Products included in the order", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderLineDto> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderLineDto> getItems() {
        return items;
    }

    public void setItems(List<OrderLineDto> items) {
        this.items = items;
    }
}
