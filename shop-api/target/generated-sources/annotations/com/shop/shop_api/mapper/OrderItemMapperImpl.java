package com.shop.shop_api.mapper;

import com.shop.shop_api.dto.OrderItemDto;
import com.shop.shop_api.model.Order;
import com.shop.shop_api.model.OrderItem;
import com.shop.shop_api.model.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-24T16:16:27+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItemDto toDto(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setOrderId( orderItemOrderId( orderItem ) );
        orderItemDto.setProductId( orderItemProductId( orderItem ) );
        orderItemDto.setProductName( orderItemProductName( orderItem ) );
        orderItemDto.setId( orderItem.getId() );
        orderItemDto.setQuantity( orderItem.getQuantity() );

        return orderItemDto;
    }

    @Override
    public OrderItem toEntity(OrderItemDto dto) {
        if ( dto == null ) {
            return null;
        }

        OrderItem orderItem = new OrderItem();

        orderItem.setId( dto.getId() );
        orderItem.setQuantity( dto.getQuantity() );

        return orderItem;
    }

    private Long orderItemOrderId(OrderItem orderItem) {
        Order order = orderItem.getOrder();
        if ( order == null ) {
            return null;
        }
        return order.getId();
    }

    private Long orderItemProductId(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }

    private String orderItemProductName(OrderItem orderItem) {
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getName();
    }
}
