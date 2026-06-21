package com.shop.shop_api.service;

import com.shop.shop_api.dto.ProductDto;
import com.shop.shop_api.mapper.ProductMapper;
import com.shop.shop_api.model.Product;
import com.shop.shop_api.repository.CategoryRepository;
import com.shop.shop_api.repository.ProductJdbcRepository;
import com.shop.shop_api.repository.ProductRepository;
import com.shop.shop_api.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final CategoryRepository categoryRepository;
    private final ProductJdbcRepository jdbcRepository;
    private final TagRepository tagRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repo,
                          CategoryRepository categoryRepository,
                          ProductJdbcRepository jdbcRepository,
                          TagRepository tagRepository,
                          ProductMapper mapper) {
        this.repo = repo;
        this.categoryRepository = categoryRepository;
        this.jdbcRepository = jdbcRepository;
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ProductDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductDto getById(Long id) {
        return mapper.toDto(getEntityById(id));
    }

    public Product getEntityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<ProductDto> getAllOptimized() {
        return repo.findAllOptimized().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<ProductDto> getPage(Pageable pageable) {
        return repo.findPageWithCategory(pageable).map(this::toDtoWithoutTags);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto create(ProductDto dto) {
        Product product = mapper.toEntity(dto);
        product.setId(null);
        product.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id " + dto.getCategoryId() + " not found")));
        product.setTags(new HashSet<>(tagRepository.findAllById(dto.getTagIds())));
        return mapper.toDto(repo.save(product));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto update(Long id, ProductDto dto) {
        Product product = getEntityById(id);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id " + dto.getCategoryId() + " not found")));
        product.setTags(new HashSet<>(tagRepository.findAllById(dto.getTagIds())));
        return mapper.toDto(repo.save(product));
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public BigDecimal getAveragePrice() {
        return jdbcRepository.getAveragePrice();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        repo.delete(getEntityById(id));
    }

    private ProductDto toDtoWithoutTags(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());
        return dto;
    }
}
