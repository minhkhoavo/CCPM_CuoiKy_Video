package com.restaurant.management.service;

import com.restaurant.management.model.Dish;
import com.restaurant.management.model.Inventory;
import com.restaurant.management.model.Recipe;
import com.restaurant.management.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private InventoryService inventoryService;

    // Phương thức cập nhật công thức cho món ăn
    public void updateRecipesForDish(Dish dish, Map<String, String> params) {
        List<Recipe> recipesToUpdate = new ArrayList<>();
        Set<Integer> processedIndexes = new HashSet<>();

        for (String key : params.keySet()) {
            if (key.startsWith("recipes[")) {
                System.out.println(key + "-----key");

                String[] parts = key.split("\\[|\\]"); // Tách key như "recipes[0].inventoryId"
                int index = Integer.parseInt(parts[1]); // Lấy chỉ số index

                if (processedIndexes.contains(index)) {
                    continue; // Nếu đã xử lý rồi, bỏ qua
                }

//                System.out.println(parts[1]+"---------------------------");
                // Lấy inventoryId và quantityRequired từ request parameters
                String inventoryIdStr = params.get("recipes[" + index + "].inventoryId");
                String quantityRequiredStr = params.get("recipes[" + index + "].quantityRequired");

                if (inventoryIdStr != null && quantityRequiredStr != null) {
                    //lay inventoryId
                    Long inventoryId = Long.valueOf(inventoryIdStr);
                    int quantityRequired = Integer.parseInt(quantityRequiredStr);

                    if (quantityRequired > 0) {
                        Inventory inventory = inventoryService.getById(inventoryId);

                        //tao recipe moi
                        if (inventory != null) {
                            Recipe recipe = new Recipe();
                            recipe.setDish(dish);
                            recipe.setInventory(inventory);
                            recipe.setQuantityRequired(quantityRequired);
                            recipesToUpdate.add(recipe);
                        }
                    }
                }
                processedIndexes.add(index);
            }
        }

        // Xóa các công thức cũ và lưu các công thức mới
        recipeRepository.deleteByDish(dish);
        recipeRepository.saveAll(recipesToUpdate);
    }
}
