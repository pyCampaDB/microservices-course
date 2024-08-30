package com.spring.cqrs.command.events;

import com.spring.cqrs.command.data.Product;
import com.spring.cqrs.command.repositories.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductEventsHandler {
    @Autowired
    private ProductRepository productRepository;

    @EventHandler
    public void on(CreateProductEvent createProductEvent){
        Product product = new Product();
        BeanUtils.copyProperties(createProductEvent, product);
        productRepository.save(product);
    }
}
