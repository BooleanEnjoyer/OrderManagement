package com.example.OrderManagement.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document(collection = "Address")
public class Address {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String street;
    private int houseNumber;
    private String city;
    private String zipCode;
}
