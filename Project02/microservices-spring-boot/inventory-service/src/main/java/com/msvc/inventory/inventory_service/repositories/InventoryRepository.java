package com.msvc.inventory.inventory_service.repositories;

import com.msvc.inventory.inventory_service.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    //Optional<Inventory> findBySkuCode(String skuCode);
    List<Inventory> findBySkuCodeIn (List<String> skuCode);
}
