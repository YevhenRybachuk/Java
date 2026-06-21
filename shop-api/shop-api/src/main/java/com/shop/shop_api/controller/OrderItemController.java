package com.shop.shop_api.controller;

import com.shop.shop_api.dto.ErrorResponseDto;
import com.shop.shop_api.dto.OrderItemDto;
import com.shop.shop_api.dto.OrderItemPageResponseDto;
import com.shop.shop_api.dto.OrderItemPatchRequestDto;
import com.shop.shop_api.service.OrderItemService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Order Items", description = "API for managing individual order items")
@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService service;

    @Operation(summary = "Get all order items", description = "Returns all individual order items.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order items returned successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderItemDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public List<OrderItemDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get order item by id", description = "Returns one order item by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order item found",
                    content = @Content(schema = @Schema(implementation = OrderItemDto.class))),
            @ApiResponse(responseCode = "404", description = "Order item not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public OrderItemDto getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(summary = "Get order item page", description = "Returns a paginated list of order items.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order item page returned successfully",
                    content = @Content(schema = @Schema(implementation = OrderItemPageResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/page")
    public Page<OrderItemDto> getPage(Pageable pageable) {
        return service.getPage(pageable);
    }

    @Operation(summary = "Create order item", description = "Creates a new order item from request data.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order item created successfully",
                    content = @Content(schema = @Schema(implementation = OrderItemDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid order item request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Referenced order or product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public OrderItemDto create(@Valid @RequestBody OrderItemDto orderItem) {
        return service.create(orderItem);
    }

    @Operation(summary = "Update order item", description = "Replaces order item data by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order item updated successfully",
                    content = @Content(schema = @Schema(implementation = OrderItemDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid order item request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Order item, order, or product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public OrderItemDto update(@PathVariable Long id,
                               @Valid @RequestBody OrderItemDto orderItem) {
        return service.update(id, orderItem);
    }

    @Operation(summary = "Partially update order item", description = "Updates only provided order item fields.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order item fields to update",
                    content = @Content(schema = @Schema(implementation = OrderItemPatchRequestDto.class))))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order item patched successfully",
                    content = @Content(schema = @Schema(implementation = OrderItemDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid patch request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Order item, order, or product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public OrderItemDto patch(@PathVariable Long id,
                              @RequestBody Map<String, Object> updates) {

        OrderItemDto orderItem = service.getById(id);

        if (updates.containsKey("orderId")) {
            orderItem.setOrderId(((Number) updates.get("orderId")).longValue());
        }
        if (updates.containsKey("productId")) {
            orderItem.setProductId(((Number) updates.get("productId")).longValue());
        }
        if (updates.containsKey("quantity")) {
            orderItem.setQuantity(((Number) updates.get("quantity")).intValue());
        }

        return service.update(id, orderItem);
    }

    @Operation(summary = "Delete order item", description = "Deletes order item by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order item not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
