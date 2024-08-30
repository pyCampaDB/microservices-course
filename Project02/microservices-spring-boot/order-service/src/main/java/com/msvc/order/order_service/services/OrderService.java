package com.msvc.order.order_service.services;

/*import brave.Span;
import brave.Tracer;*/
import com.msvc.order.order_service.config.rabbitmq.Producer;
import com.msvc.order.order_service.dto.InventoryResponse;
import com.msvc.order.order_service.dto.OrderLineItemsDto;
import com.msvc.order.order_service.dto.OrderRequest;
import com.msvc.order.order_service.events.OrderPlacedEvent;
import com.msvc.order.order_service.models.Order;
import com.msvc.order.order_service.models.OrderLineItems;
import com.msvc.order.order_service.repositories.OrderRepository;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@Service
@Transactional
public class OrderService {
    @Autowired
    private KafkaTemplate<Object, OrderPlacedEvent> kafkaTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private Tracer tracer;
    @Autowired
    private Producer producer;
    //@Transactional(readOnly = true)
    //@SneakyThrows
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::mapToDto)
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);

        List<String> skuCode = order.getOrderLineItems().stream()
                        .map(OrderLineItems::getSkuCode)
                        .toList();
        System.out.println("\nSku Codes: " + skuCode + "\n");

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        // replace withSpanInScope to withSpan because I've changed brave.Tracer
        // to io.micrometer.tracing.Tracer
        try(Tracer.SpanInScope isLookup = tracer.withSpan/*InScope*/(inventoryServiceLookup.start())){
            inventoryServiceLookup.tag("call", "inventory-service");

            InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                    .uri(
                            //"http://localhost:8082/api/inventory",
                            "http://inventory-service/api/inventory",
                            uriBuilder ->
                                    uriBuilder.queryParam("skuCode",skuCode).build()
                    ).retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            if (inventoryResponseArray != null) {
                boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
                if (allProductsInStock) {
                    orderRepository.save(order);
                    kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                    sendMessageWithRabbitMQ("Notification with RabbitMQ, Order save successfully");
                    return "Order save successfully";
                } else{
                    throw  new IllegalArgumentException("The product is not in stock");
                }
            }else {
                throw new IllegalArgumentException("There are no products in the list. The array is null");
            }
        }  finally {
            inventoryServiceLookup./*flush*/end();//replace brave.Span to io.micrometer.tracing.Span
        }


    }

    private void sendMessageWithRabbitMQ(String message){
        log.info("The message '{}' has been received successfully", message);
        producer.send(message);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setAmount(orderLineItemsDto.getAmount());
        return orderLineItems;
    }
}
