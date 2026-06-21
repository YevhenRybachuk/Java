package com.shop.shop_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Fields that can be partially updated for a customer")
public class CustomerPatchRequestDto {

    @Schema(description = "Customer full name", example = "John Smith")
    private String name;

    @Schema(description = "Customer email address", example = "john.smith@example.com")
    private String email;

    @Schema(description = "Customer phone number", example = "+380501112233")
    private String phone;

    @Schema(description = "Customer delivery address", example = "Kyiv, Khreshchatyk 1")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
