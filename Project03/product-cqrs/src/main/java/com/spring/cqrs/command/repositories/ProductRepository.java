package com.spring.cqrs.command.repositories;

import com.spring.cqrs.command.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
