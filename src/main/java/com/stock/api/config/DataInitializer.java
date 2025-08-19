package com.stock.api.config;

import com.stock.api.entity.Product;
import com.stock.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Vérifier si des données existent déjà
        if (productRepository.count() == 0) {
            // Créer des données de test
            productRepository.save(new Product("Ordinateur Portable", "Ordinateur portable Dell Inspiron 15", "DELL-INS-001", 25, new BigDecimal("899.99")));
            productRepository.save(new Product("Souris Sans Fil", "Souris optique sans fil Logitech", "LOG-MOUSE-001", 150, new BigDecimal("29.99")));
            productRepository.save(new Product("Clavier Mécanique", "Clavier mécanique RGB gaming", "GAM-KEY-001", 45, new BigDecimal("129.99")));
            productRepository.save(new Product("Écran 24 pouces", "Moniteur LED Full HD 24 pouces", "MON-24-001", 12, new BigDecimal("199.99")));
            productRepository.save(new Product("Disque Dur SSD", "SSD 500GB Samsung EVO", "SSD-SAM-500", 0, new BigDecimal("79.99")));
            productRepository.save(new Product("Webcam HD", "Webcam 1080p avec microphone intégré", "WEB-HD-001", 8, new BigDecimal("49.99")));
            productRepository.save(new Product("Casque Audio", "Casque audio stéréo professionnel", "AUD-PRO-001", 35, new BigDecimal("89.99")));
            
            System.out.println("✅ Données de test créées avec succès!");
        }
    }
}