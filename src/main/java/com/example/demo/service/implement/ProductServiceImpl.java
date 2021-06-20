package com.example.demo.service.implement;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public List<Product> getAll(){
        return productRepository.findAll();
    }
    @Override
    public Product getById(String id){
        return productRepository.findById(id).orElse(null);
    }
    @Override
    public void saveProduct(Product product){
        productRepository.save(product);
    }
    @Override
    public void deleteProduct(String id){
        productRepository.deleteById(id);
    }
}
