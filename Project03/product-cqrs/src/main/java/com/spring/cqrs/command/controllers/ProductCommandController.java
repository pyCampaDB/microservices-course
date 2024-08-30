package com.spring.cqrs.command.controllers;

import com.spring.cqrs.command.commands.CreateProductCommand;
import com.spring.cqrs.command.models.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String saveProduct(@RequestBody ProductRestModel productRestModel){
        CreateProductCommand createProductCommand = CreateProductCommand
                .builder()
                .productId(UUID.randomUUID().toString())
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .quantity(productRestModel.getQuantity())
                .build();
        return commandGateway.sendAndWait(createProductCommand);
    }
}
