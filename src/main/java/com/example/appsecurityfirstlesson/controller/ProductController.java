package com.example.appsecurityfirstlesson.controller;

import com.example.appsecurityfirstlesson.entity.Product;
import com.example.appsecurityfirstlesson.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

//    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'MANAGER')")// ROE BASED AUTHENTICATION
    @GetMapping
    public HttpEntity<?> getProduct(){
        return ResponseEntity.ok(productRepository.findAll());
    }

//    @PreAuthorize(value = "hasRole('DIRECTOR')")// ROE BASED AUTHENTICATION
    @GetMapping("/{id}")
    public HttpEntity<?> getProductById(@PathVariable Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.isPresent()
                ? new ResponseEntity<>(productOptional.get(), HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

//    @PreAuthorize(value = "hasRole('DIRECTOR')")// ROE BASED AUTHENTICATION
    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody Product product){
        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

//    @PreAuthorize(value = "hasAnyRole('MANAGER', 'DIRECTOR')")// ROE BASED AUTHENTICATION
    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable Long id, @RequestBody Product product){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product editedProduct = optionalProduct.get();
            editedProduct.setName(product.getName());
            productRepository.save(editedProduct);
            return new ResponseEntity<>("Edited",HttpStatus.OK);
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
//    @PreAuthorize(value = "hasRole('DIRECTOR')")// ROE BASED AUTHENTICATION
    public HttpEntity<?> deleteProduct(@PathVariable Long id){
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
    }
}
