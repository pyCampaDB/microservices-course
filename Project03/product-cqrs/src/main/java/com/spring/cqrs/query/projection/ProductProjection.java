package com.spring.cqrs.query.projection;

import com.spring.cqrs.command.data.Product;
import com.spring.cqrs.command.models.ProductRestModel;
import com.spring.cqrs.command.repositories.ProductRepository;
import com.spring.cqrs.query.queries.GetProductQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProjection {
    @Autowired
    private ProductRepository productRepository;

    @QueryHandler
    public List<ProductRestModel> handle(GetProductQuery getProductQuery){
        List<Product> products = productRepository.findAll();
        List<ProductRestModel> productRestModels = products.stream()
                .map(product -> ProductRestModel.builder()
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .name(product.getName())
                        .build()
                ).toList();
        return productRestModels;

    }
}
