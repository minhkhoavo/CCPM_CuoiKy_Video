package com.restaurant.management.service;

import com.restaurant.management.model.Customer;
import com.restaurant.management.model.Inventory;
import com.restaurant.management.repository.InventoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    public InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory(){
        return inventoryRepository.findAll();
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Inventory inventory) {
        Inventory existingInventory = inventoryRepository.findById(inventory.getInventoryId())
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + inventory.getInventoryId()));

        BeanUtils.copyProperties(inventory, existingInventory, "inventoryId"); // Bỏ qua `inventoryId` n
        return inventoryRepository.save(existingInventory);
    }
}
