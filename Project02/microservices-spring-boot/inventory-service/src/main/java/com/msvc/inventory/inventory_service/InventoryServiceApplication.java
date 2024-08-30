package com.msvc.inventory.inventory_service;

import com.msvc.inventory.inventory_service.models.Inventory;
import com.msvc.inventory.inventory_service.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	/*@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("oppo_reno_10");
			inventory.setAmount(1000);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("Laptop_345");
			inventory1.setAmount(300);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}*/
}
