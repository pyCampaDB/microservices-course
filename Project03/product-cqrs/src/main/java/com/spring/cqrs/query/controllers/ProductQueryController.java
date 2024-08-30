package com.spring.cqrs.query.controllers;

import com.spring.cqrs.command.models.ProductRestModel;
import com.spring.cqrs.query.queries.GetProductQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {
    @Autowired
    private QueryGateway queryGateway;
    @GetMapping
    public List<ProductRestModel> productsList(){
        GetProductQuery getProductQuery = new GetProductQuery();
        return queryGateway.query(
                        getProductQuery,
                        ResponseTypes.multipleInstancesOf(ProductRestModel.class)
                ).join();

    }
}
