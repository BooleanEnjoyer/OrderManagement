package com.example.OrderManagement.controllers;

import com.example.OrderManagement.entity.Order;
import com.example.OrderManagement.entity.Product;
import com.example.OrderManagement.response.AddingOrderResponse;
import com.example.OrderManagement.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/app/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    private static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") String id){

        Optional<Order> searchedOrder = orderService.findOrderById(id);
        return new ResponseEntity<>(searchedOrder.orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, ORDER_NOT_FOUND_MESSAGE)) , HttpStatus.OK);
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable int page,
                                                 @PathVariable int size,
                                                 @RequestParam(value = "sortOption") String sortOption,
                                                 @RequestParam(value = "sortOrder",
                                                         defaultValue = "asc") String sortOrder){

        Page<Order> searchedOrders = orderService.findOrders(page, size, sortOption, sortOrder);
        return new ResponseEntity<>(searchedOrders, HttpStatus.OK);
    }

    @GetMapping("/search/status/{page}/{size}")
    public ResponseEntity<Page<Order>> searchOrdersByStatus(@RequestParam(value = "searchTerm") String searchTerm,
                                                    @PathVariable int page,
                                                    @PathVariable int size,
                                                    @RequestParam(value = "sortOption") String sortOption,
                                                    @RequestParam(value = "sortOrder",
                                                            defaultValue = "asc") String sortOrder){

        Page<Order> searchedOrders = orderService.findByStatus(searchTerm, page, size, sortOption, sortOrder);
        return new ResponseEntity<>(searchedOrders, HttpStatus.OK);
    }

    @GetMapping("/search/trackingCode/{page}/{size}")
    public ResponseEntity<Page<Order>> searchOrdersByTrackingCode(@RequestParam(value = "searchTerm") String searchTerm,
                                                            @PathVariable int page,
                                                            @PathVariable int size,
                                                            @RequestParam(value = "sortOption") String sortOption,
                                                            @RequestParam(value = "sortOrder",
                                                                    defaultValue = "asc") String sortOrder){

        Page<Order> searchedOrders = orderService.findOrderByTrackingCode(searchTerm, page, size, sortOption, sortOrder);
        return new ResponseEntity<>(searchedOrders, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AddingOrderResponse> addOrder(@RequestBody Order addOrder){

        AddingOrderResponse addedOrderResponse = orderService.addOrder(addOrder);
        return new ResponseEntity<>(addedOrderResponse, HttpStatus.CREATED);
    }

    @PatchMapping("")
    public ResponseEntity<AddingOrderResponse>updateOrder(@RequestBody Order updateOrder){

        AddingOrderResponse updatedOrder = orderService.updateOrder(updateOrder);
        return new ResponseEntity<>(updatedOrder, HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Product>deleteProduct(@PathVariable String id){
        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
