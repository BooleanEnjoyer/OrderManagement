package com.example.OrderManagement.controllers;

import com.example.OrderManagement.entity.Product;
import com.example.OrderManagement.response.AddingProductResponse;
import com.example.OrderManagement.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/app/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id){

        Optional<Product> searchedProduct = productService.findProductById(id);
        return new ResponseEntity<>(searchedProduct.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, PRODUCT_NOT_FOUND_MESSAGE)) , HttpStatus.OK);
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Product>> getProducts(@PathVariable int page,
                                                     @PathVariable int size,
                                                     @RequestParam(value = "sortOption") String sortOption,
                                                     @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder){

        Page<Product> searchedProducts= productService.findProducts(page, size, sortOption, sortOrder);
        return new ResponseEntity<>(searchedProducts, HttpStatus.OK);
    }

    @GetMapping("/search/{page}/{size}")
    public Page<Product> searchProducts(@RequestParam(value = "searchTerm") String searchTerm,
                                        @PathVariable int page,
                                        @PathVariable int size,
                                        @RequestParam(value = "sortOption") String sortOption,
                                        @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder){
        return productService.searchProducts(searchTerm, page, size, sortOption, sortOrder);
    }

    @PostMapping("")
    public ResponseEntity<AddingProductResponse>addProduct(@RequestBody Product addProduct){

        AddingProductResponse product = productService.addProduct(addProduct);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PatchMapping("")
    public ResponseEntity<AddingProductResponse>updateProduct(@RequestBody Product updateProduct){

        AddingProductResponse updatedProduct = productService.updateProduct(updateProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Product>deleteProduct(@PathVariable String id){
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
