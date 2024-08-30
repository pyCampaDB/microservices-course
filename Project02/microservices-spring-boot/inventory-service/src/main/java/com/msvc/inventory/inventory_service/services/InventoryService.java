package com.msvc.inventory.inventory_service.services;

import com.msvc.inventory.inventory_service.dto.InventoryResponse;
import com.msvc.inventory.inventory_service.repositories.InventoryRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public /*boolean*/List<InventoryResponse> isInStock(List<String> skuCode){
        //return inventoryRepository.findBySkuCode(skuCode).isPresent();
        log.info("Wait started");
        /*Thread.sleep(10000);
        log.info("Wait end");*/
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .inStock(inventory.getAmount() > 0)
                            .build()
                ).collect(Collectors.toList());
    }
}
