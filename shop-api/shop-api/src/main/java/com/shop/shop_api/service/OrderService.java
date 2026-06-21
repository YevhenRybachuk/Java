package com.shop.shop_api.service;

import com.shop.shop_api.dto.OrderDto;
import com.shop.shop_api.dto.OrderLineDto;
import com.shop.shop_api.model.Order;
import com.shop.shop_api.model.OrderItem;
import com.shop.shop_api.model.Product;
import com.shop.shop_api.repository.CustomerRepository;
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
public class OrderService {

    private final OrderRepository repo;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository repo,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.repo = repo;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<OrderDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public OrderDto getById(Long id) {
        return toDto(getEntityById(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<OrderDto> getPage(Pageable pageable) {
        return repo.findAll(pageable).map(this::toPageDto);
    }

    public Order getEntityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + id + " not found"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto create(OrderDto dto) {
        Order order = toEntity(dto);
        order.setId(null);
        return toDto(repo.save(order));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto update(Long id, OrderDto dto) {
        Order order = getEntityById(id);
        order.setCustomer(customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + dto.getCustomerId() + " not found")));
        order.setItems(dto.getItems().stream().map(this::toOrderItemEntity).toList());
        return toDto(repo.save(order));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        repo.delete(getEntityById(id));
    }

    private OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getName());
        dto.setItems(order.getItems().stream().map(this::toOrderItemDto).toList());
        return dto;
    }

    private OrderDto toPageDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getName());
        return dto;
    }

    private OrderLineDto toOrderItemDto(OrderItem item) {
        OrderLineDto dto = new OrderLineDto();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        return dto;
    }

    private Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setCustomer(customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + dto.getCustomerId() + " not found")));
        order.setItems(dto.getItems().stream().map(this::toOrderItemEntity).toList());
        return order;
    }

    private OrderItem toOrderItemEntity(OrderLineDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + dto.getProductId() + " not found"));
        OrderItem item = new OrderItem();
        item.setId(dto.getId());
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        return item;
    }
}
