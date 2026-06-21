package com.shop.shop_api.controller;

import com.shop.shop_api.dto.CategoryDto;
import com.shop.shop_api.dto.CategoryPageResponseDto;
import com.shop.shop_api.dto.CategoryPatchRequestDto;
import com.shop.shop_api.dto.ErrorResponseDto;
import com.shop.shop_api.service.CategoryService;
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
import java.util.Map;

@Tag(name = "Categories", description = "API for managing product categories")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Operation(summary = "Get all categories", description = "Returns all product categories.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories returned successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public List<CategoryDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get category by id", description = "Returns one category by its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public CategoryDto getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(summary = "Get category page", description = "Returns a paginated list of categories.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category page returned successfully",
                    content = @Content(schema = @Schema(implementation = CategoryPageResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/page")
    public Page<CategoryDto> getPage(Pageable pageable) {
        return service.getPage(pageable);
    }

    @Operation(summary = "Create category", description = "Creates a new product category from request data.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category created successfully",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public CategoryDto create(@Valid @RequestBody CategoryDto category) {
        return service.create(category);
    }

    @Operation(summary = "Update category", description = "Replaces category data by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable Long id,
                              @Valid @RequestBody CategoryDto category) {
        return service.update(id, category);
    }

    @Operation(summary = "Partially update category", description = "Updates only provided category fields.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category fields to update",
                    content = @Content(schema = @Schema(implementation = CategoryPatchRequestDto.class))))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category patched successfully",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid patch request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public CategoryDto patch(@PathVariable Long id,
                             @RequestBody Map<String, Object> updates) {

        CategoryDto category = service.getById(id);

        if (updates.containsKey("name")) {
            category.setName((String) updates.get("name"));
        }

        return service.update(id, category);
    }

    @Operation(summary = "Delete category", description = "Deletes category by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
