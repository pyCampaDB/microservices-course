package com.spring.cqrs.command.aggregates;

import com.spring.cqrs.command.commands.CreateProductCommand;
import com.spring.cqrs.command.events.CreateProductEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Aggregate //this entity must be managed by axon
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand){
        CreateProductEvent createProductEvent = CreateProductEvent
                .builder()
                .build();
        BeanUtils.copyProperties(createProductCommand,createProductEvent);
        AggregateLifecycle.apply(createProductEvent); //register events
    }

    @EventSourcingHandler //create the event
    public void on(CreateProductEvent createProductEvent){
        this.quantity= createProductEvent.getQuantity();
        this.price=createProductEvent.getPrice();
        this.name=createProductEvent.getName();
        this.productId= createProductEvent.getProductId();
    }
}
