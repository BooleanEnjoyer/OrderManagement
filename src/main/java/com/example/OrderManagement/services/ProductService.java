package com.example.OrderManagement.services;

import com.example.OrderManagement.entity.Product;
import com.example.OrderManagement.repositories.ProductRepository;
import com.example.OrderManagement.response.AddingProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private static final String SUCCESSFUL_PRODUCT_ADDING_MESSAGE = "Successfully added your product";
    private static final String SUCCESSFUL_PRODUCT_UPDATING_MESSAGE = "Successfully updated your product";
    private static final String DESC_SORTING = "desc";
    private static final String PRICE_SORTING = "price";
    private static final String NAME_SORTING = "name";

    public Optional<Product> findProductById(String id){
        return productRepository.findProductById(id);
    }

    public Page<Product> findProducts(int page, int size, String sortOption, String sortOrder){
        Pageable pageable = createPageable(page, size, sortOption, sortOrder);
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProducts(String searchTerm, int page, int size, String sortOption, String sortOrder){
        Pageable pageable = createPageable(page, size, sortOption, sortOrder);
        return productRepository.findProductByNameContainingIgnoreCase(searchTerm, pageable);
    }

    protected Pageable createPageable(int page, int size, String sortOption, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase(DESC_SORTING) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort;
        switch (sortOption) {
            case NAME_SORTING -> sort = Sort.by(direction, NAME_SORTING);
            case PRICE_SORTING -> sort = Sort.by(direction, PRICE_SORTING);
            default -> sort = Sort.unsorted();
        }

        return PageRequest.of(page, size, sort);
    }

    public AddingProductResponse addProduct(Product addedProduct){

        var addingProductResponse = new AddingProductResponse();

        var product = Product.builder().
                name(addedProduct.getName()).
                price(addedProduct.getPrice()).
                build();
        var addingProduct = productRepository.save(product);
        addingProductResponse.setProduct(addingProduct);
        addingProductResponse.setMessage(SUCCESSFUL_PRODUCT_ADDING_MESSAGE);
        return addingProductResponse;
    }

    public AddingProductResponse updateProduct(Product updatedProduct){

        var addingProductResponse = new AddingProductResponse();
        var product = Product.builder().
                id(updatedProduct.getId()).
                name(updatedProduct.getName()).
                price(updatedProduct.getPrice()).
                build();
        var addingProduct = productRepository.save(product);
        addingProductResponse.setProduct(addingProduct);
        addingProductResponse.setMessage(SUCCESSFUL_PRODUCT_UPDATING_MESSAGE);
        return addingProductResponse;
    }

    public void deleteProductById(String id){
        productRepository.deleteById(id);
    }

}
