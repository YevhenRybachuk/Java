package com.shop.shop_api.service;

import com.shop.shop_api.dto.OrderItemDto;
import com.shop.shop_api.mapper.OrderItemMapper;
import com.shop.shop_api.model.OrderItem;
import com.shop.shop_api.repository.OrderItemRepository;
import com.shop.shop_api.repository.OrderRepository;
import com.shop.shop_api.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository repo;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemMapper mapper;

    public OrderItemService(OrderItemRepository repo,
                            OrderRepository orderRepository,
                            ProductRepository productRepository,
                            OrderItemMapper mapper) {
        this.repo = repo;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<OrderItemDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public OrderItemDto getById(Long id) {
        return mapper.toDto(getEntityById(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<OrderItemDto> getPage(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    public OrderItem getEntityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order item with id " + id + " not found"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemDto create(OrderItemDto dto) {
        OrderItem orderItem = mapper.toEntity(dto);
        orderItem.setId(null);
        orderItem.setOrder(orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + dto.getOrderId() + " not found")));
        orderItem.setProduct(productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + dto.getProductId() + " not found")));
        return mapper.toDto(repo.save(orderItem));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemDto update(Long id, OrderItemDto dto) {
        OrderItem orderItem = getEntityById(id);
        if (dto.getOrderId() != null) {
            orderItem.setOrder(orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + dto.getOrderId() + " not found")));
        }
        orderItem.setProduct(productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + dto.getProductId() + " not found")));
        orderItem.setQuantity(dto.getQuantity());
        return mapper.toDto(repo.save(orderItem));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        repo.delete(getEntityById(id));
    }
}
