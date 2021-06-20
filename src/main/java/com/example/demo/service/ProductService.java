package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product getById(String id);
    void saveProduct(Product product);
    void deleteProduct(String id);
}
