package com.spring.cqrs.command.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRestModel {
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
