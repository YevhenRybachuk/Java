package com.shop.shop_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Fields that can be partially updated for a category")
public class CategoryPatchRequestDto {

    @Schema(description = "New category name", example = "Electronics")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
