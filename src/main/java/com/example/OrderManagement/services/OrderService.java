package com.example.OrderManagement.services;

import com.example.OrderManagement.entity.Order;
import com.example.OrderManagement.entity.Product;
import com.example.OrderManagement.entity.enums.OrderStatus;
import com.example.OrderManagement.repositories.OrderRepository;
import com.example.OrderManagement.response.AddingAddressResponse;
import com.example.OrderManagement.response.AddingOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final AddressService addressService;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    private static final String SUCCESSFUL_ORDER_ADDING_MESSAGE = "Successfully added your order";
    private static final String SUCCESSFUL_ORDER_UPDATING_MESSAGE = "Successfully updated your order";

    private static final String DESC_SORTING = "desc";
    private static final String STATUS_SORTING = "status";
    private static final String TRACKING_CODE_SORTING = "trackingCode";

    public Optional<Order> findOrderById(String id){
        return orderRepository.findOrderById(id);
    }

    public Page<Order> findOrderByTrackingCode(String searchTerm, int page, int size, String sortOption, String sortOrder){
        Pageable pageable = createPageable(page, size, sortOption, sortOrder);
        return orderRepository.findAllByTrackingCodeContainingIgnoreCase(searchTerm , pageable);
    }

    public Page<Order> findByStatus(String searchTerm, int page, int size, String sortOption, String sortOrder){
        Pageable pageable = createPageable(page, size, sortOption, sortOrder);
        return orderRepository.findAllByStatusContainingIgnoreCase(searchTerm, pageable);
    }

    public Page<Order> findOrders(int page, int size, String sortOption, String sortOrder){
        Pageable pageable = createPageable(page, size, sortOption, sortOrder);
        return orderRepository.findAll(pageable);
    }

    private Pageable createPageable(int page, int size, String sortOption, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase(DESC_SORTING) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort;
        switch (sortOption) {
            case STATUS_SORTING -> sort = Sort.by(direction, STATUS_SORTING);
            case TRACKING_CODE_SORTING -> sort = Sort.by(direction, TRACKING_CODE_SORTING);
            default -> sort = Sort.unsorted();
        }

        return PageRequest.of(page, size, sort);
    }

    public AddingOrderResponse addOrder(Order addedOrder){

        AddingOrderResponse addingOrderResponse = new AddingOrderResponse();
        AddingAddressResponse startingAddressResponse = addressService.addAddress(addedOrder.getStartingAddress());
        AddingAddressResponse targetAddressResponse = addressService.addAddress(addedOrder.getTargetAddress());
        Product product = productService.findProductById(addedOrder.getProduct().getId()).orElseThrow();

        var order = Order.builder()
                .trackingCode(UUID.randomUUID().toString())
                .status(OrderStatus.PLACED.getLabel())
                .startingAddress(startingAddressResponse.getAddress())
                .targetAddress(targetAddressResponse.getAddress())
                .product(product)
                .dateCreated(new Date(System.currentTimeMillis()))
                .lastUpdated(new Date(System.currentTimeMillis()))
                .build();

        orderRepository.save(order);
        addingOrderResponse.setTrackingCode(order.getTrackingCode());
        addingOrderResponse.setMessage(SUCCESSFUL_ORDER_ADDING_MESSAGE);
        return addingOrderResponse;
    }

    public AddingOrderResponse updateOrder(Order addedOrder){

        var addingOrderResponse = new AddingOrderResponse();
        Order fetchedOrder = findOrderById(addedOrder.getId()).orElseThrow();
        var order = Order.builder()
                .id(addedOrder.getId())
                .status(addedOrder.getStatus())
                .product(fetchedOrder.getProduct())
                .startingAddress(fetchedOrder.getStartingAddress())
                .targetAddress(fetchedOrder.getTargetAddress())
                .dateCreated(fetchedOrder.getDateCreated())
                .trackingCode(fetchedOrder.getTrackingCode())
                .lastUpdated(new Date(System.currentTimeMillis()))
                .build();
        orderRepository.save(order);
        addingOrderResponse.setMessage(SUCCESSFUL_ORDER_UPDATING_MESSAGE);
        addingOrderResponse.setTrackingCode(order.getTrackingCode());
        return addingOrderResponse;
    }

    public void deleteOrderById(String id){
        orderRepository.deleteById(id);
    }
}
