package com.example.OrderManagement.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document(collection = "Product")
public class Product {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String name;
    private int price;
}
