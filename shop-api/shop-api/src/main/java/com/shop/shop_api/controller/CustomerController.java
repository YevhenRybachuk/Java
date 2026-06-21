package com.shop.shop_api.controller;

import com.shop.shop_api.dto.CustomerDto;
import com.shop.shop_api.dto.CustomerPageResponseDto;
import com.shop.shop_api.dto.CustomerPatchRequestDto;
import com.shop.shop_api.dto.ErrorResponseDto;
import com.shop.shop_api.service.CustomerService;
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

@Tag(name = "Customers", description = "API for managing customers")
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Operation(summary = "Get all customers", description = "Returns all registered customers.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customers returned successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDto.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public List<CustomerDto> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get customer by id", description = "Returns one customer by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public CustomerDto getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(summary = "Get customer page", description = "Returns a paginated list of customers.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer page returned successfully",
                    content = @Content(schema = @Schema(implementation = CustomerPageResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/page")
    public Page<CustomerDto> getPage(Pageable pageable) {
        return service.getPage(pageable);
    }

    @Operation(summary = "Create customer", description = "Creates a new customer from request data.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer created successfully",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid customer request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public CustomerDto create(@Valid @RequestBody CustomerDto customer) {
        return service.create(customer);
    }

    @Operation(summary = "Update customer", description = "Replaces customer data by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid customer request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public CustomerDto update(@PathVariable Long id,
                              @Valid @RequestBody CustomerDto customer) {
        return service.update(id, customer);
    }

    @Operation(summary = "Partially update customer", description = "Updates only provided customer fields.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer fields to update",
                    content = @Content(schema = @Schema(implementation = CustomerPatchRequestDto.class))))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer patched successfully",
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid patch request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public CustomerDto patch(@PathVariable Long id,
                             @RequestBody Map<String, Object> updates) {

        CustomerDto customer = service.getById(id);

        if (updates.containsKey("name")) {
            customer.setName((String) updates.get("name"));
        }
        if (updates.containsKey("email")) {
            customer.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("phone")) {
            customer.setPhone((String) updates.get("phone"));
        }
        if (updates.containsKey("address")) {
            customer.setAddress((String) updates.get("address"));
        }

        return service.update(id, customer);
    }

    @Operation(summary = "Delete customer", description = "Deletes customer by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
