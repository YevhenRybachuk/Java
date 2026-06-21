package com.shop.shop_api.controller;

import com.shop.shop_api.dto.ErrorResponseDto;
import com.shop.shop_api.dto.ProductDto;
import com.shop.shop_api.dto.ProductPageResponseDto;
import com.shop.shop_api.dto.ProductPatchRequestDto;
import com.shop.shop_api.service.ProductService;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Tag(name = "Products", description = "API for managing products")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Operation(summary = "Get all products", description = "Returns all products with their category and tag information.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products returned successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public List<ProductDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get product by id", description = "Returns one product by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ProductDto getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(summary = "Get optimized product list", description = "Returns all products using an optimized repository query.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products returned successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/optimized")
    public List<ProductDto> getAllOptimized() {
        return service.getAllOptimized();
    }

    @Operation(summary = "Get product page", description = "Returns a paginated list of products.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product page returned successfully",
                    content = @Content(schema = @Schema(implementation = ProductPageResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/page")
    public Page<ProductDto> getPage(Pageable pageable) {
        return service.getPage(pageable);
    }

    @Operation(summary = "Get average product price", description = "Returns the average price across all products.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Average price returned successfully",
                    content = @Content(schema = @Schema(implementation = BigDecimal.class, example = "749.50"))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/average-price")
    public BigDecimal getAveragePrice() {
        return service.getAveragePrice();
    }

    @Operation(summary = "Create product", description = "Creates a new product from request data.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Referenced category not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ProductDto create(@Valid @RequestBody ProductDto product) {
        return service.create(product);
    }

    @Operation(summary = "Update product", description = "Replaces product data by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Product or referenced category not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id,
                             @Valid @RequestBody ProductDto product) {
        return service.update(id, product);
    }

    @Operation(summary = "Partially update product", description = "Updates only provided product fields.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product fields to update",
                    content = @Content(schema = @Schema(implementation = ProductPatchRequestDto.class))))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product patched successfully",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid patch request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Product or referenced category not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public ProductDto patch(@PathVariable Long id,
                            @RequestBody Map<String, Object> updates) {

        ProductDto product = service.getById(id);

        if (updates.containsKey("name")) {
            product.setName((String) updates.get("name"));
        }
        if (updates.containsKey("price")) {
            product.setPrice(new BigDecimal(updates.get("price").toString()));
        }
        if (updates.containsKey("categoryId")) {
            product.setCategoryId(((Number) updates.get("categoryId")).longValue());
        }

        return service.update(id, product);
    }

    @Operation(summary = "Delete product", description = "Deletes product by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
