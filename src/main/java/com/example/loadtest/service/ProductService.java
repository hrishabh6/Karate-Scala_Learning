package com.example.loadtest.service;
import com.example.loadtest.model.Product;
import com.example.loadtest.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product updatePrice(Long id, Double newPrice) {
        return repository.findById(id).map(p -> {
            p.setPrice(newPrice);
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}