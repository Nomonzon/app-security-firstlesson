package com.example.appsecurityfirstlesson.repository;


import com.example.appsecurityfirstlesson.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
