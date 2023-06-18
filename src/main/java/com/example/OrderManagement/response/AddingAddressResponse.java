package com.example.OrderManagement.response;

import com.example.OrderManagement.entity.Address;
import lombok.Data;

@Data
public class AddingAddressResponse {
    private String message;
    private Address address;
}
