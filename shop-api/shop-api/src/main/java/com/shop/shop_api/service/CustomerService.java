package com.shop.shop_api.service;

import com.shop.shop_api.dto.CustomerDto;
import com.shop.shop_api.model.Customer;
import com.shop.shop_api.model.CustomerProfile;
import com.shop.shop_api.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CustomerDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CustomerDto getById(Long id) {
        return toDto(getEntityById(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<CustomerDto> getPage(Pageable pageable) {
        return repo.findAll(pageable).map(this::toDto);
    }

    public Customer getEntityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + id + " not found"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerDto create(CustomerDto dto) {
        Customer customer = toEntity(dto);
        customer.setId(null);
        return toDto(repo.save(customer));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerDto update(Long id, CustomerDto dto) {
        Customer customer = getEntityById(id);
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setProfile(toProfile(dto));
        return toDto(repo.save(customer));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        repo.delete(getEntityById(id));
    }

    private CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        if (customer.getProfile() != null) {
            dto.setPhone(customer.getProfile().getPhone());
            dto.setAddress(customer.getProfile().getAddress());
        }
        return dto;
    }

    private Customer toEntity(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setProfile(toProfile(dto));
        return customer;
    }

    private CustomerProfile toProfile(CustomerDto dto) {
        if (dto.getPhone() == null && dto.getAddress() == null) {
            return null;
        }

        CustomerProfile profile = new CustomerProfile();
        profile.setPhone(dto.getPhone());
        profile.setAddress(dto.getAddress());
        return profile;
    }
}
