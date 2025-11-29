package com.santoshdev.ecommerce_backend.service;

import com.santoshdev.ecommerce_backend.model.Product;
import com.santoshdev.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public String updateProduct(int id, Product updatedProduct) {
        Optional<Product> existingOpt = productRepository.findById(id);
        if (!existingOpt.isPresent()) {
            return "Product with id " + id + " not found.";
        }

        Product existing = existingOpt.get();
        // Copy fields from updatedProduct to existing. Preserve id.
        if (updatedProduct.getName() != null) existing.setName(updatedProduct.getName());
        if (updatedProduct.getDescription() != null) existing.setDescription(updatedProduct.getDescription());
        if (updatedProduct.getBrand() != null) existing.setBrand(updatedProduct.getBrand());
        if (updatedProduct.getPrice() != null) existing.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getCategory() != null) existing.setCategory(updatedProduct.getCategory());
        if (updatedProduct.getReleaseDate() != null) existing.setReleaseDate(updatedProduct.getReleaseDate());
        if (updatedProduct.getAvailable() != null) existing.setAvailable(updatedProduct.getAvailable());
        if (updatedProduct.getQuantity() != null) existing.setQuantity(updatedProduct.getQuantity());

        productRepository.save(existing);
        return "Product with id " + id + " has been updated.";
    }

    public String deleteProduct(int id) {
        productRepository.deleteById(id);
        return "Product with id " + id + " has been deleted.";
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }
}
