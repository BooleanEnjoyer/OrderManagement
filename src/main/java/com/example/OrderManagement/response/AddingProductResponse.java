package com.example.OrderManagement.response;

import com.example.OrderManagement.entity.Product;
import lombok.Data;

@Data
public class AddingProductResponse {
    private String message;
    private Product product;
}
