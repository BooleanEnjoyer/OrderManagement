package com.example.OrderManagement.repositories;

import com.example.OrderManagement.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findProductById(String id);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findProductByNameContainingIgnoreCase(String name, Pageable pageable);
}
