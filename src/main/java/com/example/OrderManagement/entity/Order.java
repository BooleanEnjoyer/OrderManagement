package com.example.OrderManagement.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.*;
import java.util.Date;

@Data
@Builder
@Document(collection = "Order")
public class Order {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String trackingCode;
    @Field
    private String status;
    @DBRef
    private Address startingAddress;
    @DBRef
    private Address targetAddress;
    @DBRef
    private Product product;
    private Date dateCreated;
    private Date lastUpdated;
}

