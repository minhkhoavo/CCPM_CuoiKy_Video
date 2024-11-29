package com.restaurant.management.service;

import com.restaurant.management.model.Dish;
import com.restaurant.management.model.Inventory;
import com.restaurant.management.model.Recipe;
import com.restaurant.management.repository.DishRepository;
import com.restaurant.management.repository.InventoryRepository;
import com.restaurant.management.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Optional<Dish> getDishById(Long id) {
        return dishRepository.findById(id);
    }

    public Dish saveDish(Dish dish, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String imageUrl = storageService.uploadImage(file);
            dish.setImage(imageUrl);
        }
        return dishRepository.save(dish);
    }

    public void updateDish(Long id, Dish updatedDish, MultipartFile file) throws IOException {
        Dish existingDish = dishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dish not found with id: " + id));

        existingDish.setName(updatedDish.getName());
        existingDish.setDescription(updatedDish.getDescription());
        existingDish.setPrice(updatedDish.getPrice());
        existingDish.setCategory(updatedDish.getCategory());

        if (file != null && !file.isEmpty()) {
            existingDish.setImage(storageService.uploadImage(file));
        }

        dishRepository.save(existingDish);
    }

    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }

    public List<Dish> getDishesByCategory(Long categoryId) {
        return dishRepository.findByCategoryId(categoryId);
    }

    public void processDish(Long dishID) throws IllegalAccessException {
        List<Recipe> recipes = recipeRepository.findRecipeByDishId(dishID);
        for (Recipe recipe : recipes) {
            Inventory inventory = recipe.getInventory();
            int newQuantity = inventory.getQuantity() - recipe.getQuantityRequired();

            if(newQuantity <= 0) {
                throw new IllegalAccessException("Not enough inventory for item: " + inventory.getItemName());
            }

            inventory.setQuantity(newQuantity);
            inventoryRepository.save(inventory);
        }
    }
}
