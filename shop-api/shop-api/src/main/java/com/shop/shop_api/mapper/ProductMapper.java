package com.shop.shop_api.mapper;

import com.shop.shop_api.dto.ProductDto;
import com.shop.shop_api.model.Product;
import com.shop.shop_api.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "tagIds", expression = "java(toTagIds(product.getTags()))")
    @Mapping(target = "tagNames", expression = "java(toTagNames(product.getTags()))")
    ProductDto toDto(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Product toEntity(ProductDto dto);

    default List<Long> toTagIds(Set<Tag> tags) {
        return tags == null ? List.of() : tags.stream().map(Tag::getId).toList();
    }

    default List<String> toTagNames(Set<Tag> tags) {
        return tags == null ? List.of() : tags.stream().map(Tag::getName).toList();
    }
}
