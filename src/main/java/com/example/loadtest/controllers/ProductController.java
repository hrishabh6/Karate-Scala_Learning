package com.example.loadtest.controllers;
import com.example.loadtest.model.Product;
import com.example.loadtest.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.createProduct(product);
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAllProducts();
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return service.updatePrice(id, product.getPrice());
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.getProductById(id);
    }


}
