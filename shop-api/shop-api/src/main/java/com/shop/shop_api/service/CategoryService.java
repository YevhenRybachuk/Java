package com.shop.shop_api.service;

import com.shop.shop_api.dto.CategoryDto;
import com.shop.shop_api.mapper.CategoryMapper;
import com.shop.shop_api.model.Category;
import com.shop.shop_api.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repo;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repo, CategoryMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CategoryDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CategoryDto getById(Long id) {
        return mapper.toDto(getEntityById(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<CategoryDto> getPage(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    public Category getEntityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id " + id + " not found"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryDto create(CategoryDto dto) {
        Category category = mapper.toEntity(dto);
        category.setId(null);
        return mapper.toDto(repo.save(category));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = getEntityById(id);
        category.setName(dto.getName());
        return mapper.toDto(repo.save(category));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        repo.delete(getEntityById(id));
    }
}
