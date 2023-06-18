package com.example.OrderManagement.repository;

import com.example.OrderManagement.entity.Address;
import com.example.OrderManagement.entity.Order;
import com.example.OrderManagement.entity.Product;
import com.example.OrderManagement.entity.enums.OrderStatus;
import com.example.OrderManagement.repositories.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Calendar;
import java.util.Date;


@DataMongoTest
@TestPropertySource(locations="classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    public void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    public void givenValidOrder_whenSaveOrder_thenShouldSaveOrderInDatabase(){

        //Arrange
        Address startingAddress = Address.builder().
                id("648c5303a414661a25c12db7").
                city("Zielona Góra").
                zipCode("65-419").
                street("Generała Józefa Sowińskiego").
                houseNumber(38).build();

        Address targetAddress = Address.builder().
                id("648c5412a414661a25c12dbc").
                city("Warszawa").
                zipCode("22-555").
                street("Prosta").
                houseNumber(22).build();

        Product product = Product.builder().
                id("648c4dbf5e141c609cd32a84").
                name("Suszarka").
                price(222).build();

        Order order = Order.builder().
                trackingCode("4446390d-4f15-4654-833d-21007e680444").
                startingAddress(startingAddress).
                targetAddress(targetAddress).
                status(OrderStatus.PLACED.getLabel()).
                product(product).
                dateCreated(new Date(2014, Calendar.JANUARY, 11)).
                lastUpdated(new Date(2014, Calendar.JANUARY, 11)).
                build();
        //Act
        Order savedOrder = orderRepository.save(order);
        //Assert
        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getId()).isNotNull();
        Assertions.assertThat(savedOrder.getStartingAddress().getCity()).isEqualTo("Zielona Góra");
    }
}
