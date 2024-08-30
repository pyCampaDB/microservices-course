package com.msvc.inventory.inventory_service.controllers;

import com.msvc.inventory.inventory_service.dto.InventoryResponse;
import com.msvc.inventory.inventory_service.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @GetMapping/*("/{skuCode}")*/
    @ResponseStatus(HttpStatus.OK)
    public /*boolean*/List<InventoryResponse> isInStock(@RequestParam List<String> skuCode/*@PathVariable("skuCode") String skuCode*/){
        return inventoryService.isInStock(skuCode);
    }
}
