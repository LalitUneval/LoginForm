package com.lalit.securityDemo.contoller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    // Static record to represent Product
    private static record Product(Integer productId, String productName, double price) {}

    // List to store products
    private final List<Product> products = new ArrayList<>();

    // Constructor to pre-populate some products
    public ProductController() {
        products.add(new Product(1, "iPhone", 9999.99));
        products.add(new Product(2, "Mac Pro", 2000.00));
    }

    // Get all products
    @GetMapping
    public List<Product> getProducts() {
        return products;
    }

    // Add a product
    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        products.add(product);
        return product;
    }
}
