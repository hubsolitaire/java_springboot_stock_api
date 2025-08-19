package com.stock.api.service;

import com.stock.api.entity.Product;
import com.stock.api.exception.ProductNotFoundException;
import com.stock.api.exception.DuplicateReferenceException;
import com.stock.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit avec ID " + id + " non trouvé"));
    }
    
    public Product getProductByReference(String reference) {
        return productRepository.findByReference(reference)
                .orElseThrow(() -> new ProductNotFoundException("Produit avec référence " + reference + " non trouvé"));
    }
    
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    public Product createProduct(Product product) {
        if (productRepository.existsByReference(product.getReference())) {
            throw new DuplicateReferenceException("Un produit avec la référence " + product.getReference() + " existe déjà");
        }
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        
        // Vérifier si la référence n'est pas déjà utilisée par un autre produit
        if (!product.getReference().equals(productDetails.getReference()) && 
            productRepository.existsByReference(productDetails.getReference())) {
            throw new DuplicateReferenceException("Un produit avec la référence " + productDetails.getReference() + " existe déjà");
        }
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setReference(productDetails.getReference());
        product.setQuantity(productDetails.getQuantity());
        product.setPrice(productDetails.getPrice());
        
        return productRepository.save(product);
    }
    
    public Product updateStock(Long id, Integer quantity) {
        Product product = getProductById(id);
        product.setQuantity(quantity);
        return productRepository.save(product);
    }
    
    public Product addStock(Long id, Integer quantityToAdd) {
        Product product = getProductById(id);
        product.setQuantity(product.getQuantity() + quantityToAdd);
        return productRepository.save(product);
    }
    
    public Product removeStock(Long id, Integer quantityToRemove) {
        Product product = getProductById(id);
        int newQuantity = product.getQuantity() - quantityToRemove;
        
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stock insuffisant. Stock actuel: " + product.getQuantity());
        }
        
        product.setQuantity(newQuantity);
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
    
    public List<Product> getLowStockProducts(Integer minQuantity) {
        return productRepository.findProductsWithLowStock(minQuantity);
    }
    
    public List<Product> getOutOfStockProducts() {
        return productRepository.findOutOfStockProducts();
    }
}