package com.shop.shop_api.controller;

import com.shop.shop_api.dto.ErrorResponseDto;
import com.shop.shop_api.dto.OrderDto;
import com.shop.shop_api.dto.OrderPageResponseDto;
import com.shop.shop_api.dto.OrderPatchRequestDto;
import com.shop.shop_api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders", description = "API for managing customer orders")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @Operation(summary = "Get all orders", description = "Returns all customer orders with their items.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders returned successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public List<OrderDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get order by id", description = "Returns one order by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public OrderDto getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(summary = "Get order page", description = "Returns a paginated list of orders.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order page returned successfully",
                    content = @Content(schema = @Schema(implementation = OrderPageResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/page")
    public Page<OrderDto> getPage(Pageable pageable) {
        return service.getPage(pageable);
    }

    @Operation(summary = "Create order", description = "Creates a new customer order from request data.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid order request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Referenced customer or product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public OrderDto create(@Valid @RequestBody OrderDto order) {
        return service.create(order);
    }

    @Operation(summary = "Update order", description = "Replaces order data by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order updated successfully",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid order request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Order, customer, or product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public OrderDto update(@PathVariable Long id,
                           @Valid @RequestBody OrderDto order) {
        return service.update(id, order);
    }

    @Operation(summary = "Partially update order", description = "Updates provided order fields and items.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order fields to update",
                    content = @Content(schema = @Schema(implementation = OrderPatchRequestDto.class))))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order patched successfully",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid patch request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Order, customer, or product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public OrderDto patch(@PathVariable Long id,
                          @RequestBody OrderDto updates) {

        OrderDto order = service.getById(id);

        if (updates.getCustomerId() != null) {
            order.setCustomerId(updates.getCustomerId());
        }
        if (updates.getItems() != null) {
            order.setItems(updates.getItems());
        }

        return service.update(id, order);
    }

    @Operation(summary = "Delete order", description = "Deletes order by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
