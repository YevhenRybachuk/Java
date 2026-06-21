package com.shop.shop_api.mapper;

import com.shop.shop_api.dto.CategoryDto;
import com.shop.shop_api.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto dto);
}
