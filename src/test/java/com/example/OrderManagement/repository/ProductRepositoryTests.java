package com.example.OrderManagement.repository;

import com.example.OrderManagement.entity.Address;
import com.example.OrderManagement.entity.Order;
import com.example.OrderManagement.entity.Product;
import com.example.OrderManagement.entity.enums.OrderStatus;
import com.example.OrderManagement.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import java.util.List;

@DataMongoTest
@TestPropertySource(locations="classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    public void givenValidProduct_whenSaveProduct_thenShouldSaveProductInDatabase(){

        //Arrange
        Product product = Product.builder().
                name("Suszarka").
                price(222).build();

        //Act
        Product savedProduct = productRepository.save(product);
        //Assert
        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).isNotNull();
        Assertions.assertThat(savedProduct.getPrice()).isEqualTo(222);
    }

    @Test
    public void givenProductName_whenFindProductByNameContainingIgnoreCase_thenShouldFindOrderInDatabase(){

        //Arrange
        Product product1 = Product.builder().
                name("Piłka").
                price(111).build();

        Product product2 = Product.builder().
                name("Maskotka węża").
                price(22).build();

        Product product3 = Product.builder().
                name("Piżama").
                price(12).build();

        Product product4 = Product.builder().
                name("Myszka gamingowa").
                price(321).build();

        Product product5 = Product.builder().
                name("Klawiatura").
                price(23).build();

        List<Product> productList = List.of(product1,product2,product3,product4,product5);

        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "name");
        Pageable pageable = PageRequest.of(0, 3, sort);
        String searchTerm = "pi";
        productRepository.saveAll(productList);
        //Act
        Page<Product> productPage = productRepository.findProductByNameContainingIgnoreCase(searchTerm, pageable);
        //Assert
        Assertions.assertThat(productPage.getContent().get(0)).isNotNull();
        Assertions.assertThat(productPage.getContent().get(0).getName()).isEqualTo("Piżama");
        Assertions.assertThat(productPage.getNumberOfElements()).isEqualTo(2);
    }
}
