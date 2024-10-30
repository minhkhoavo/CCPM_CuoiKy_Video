package com.restaurant.management.service;

import com.restaurant.management.model.Dish;
import com.restaurant.management.repository.DishRepository;
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

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Optional<Dish> getDishById(Long id) {
        return dishRepository.findById(id);
    }

    public Dish saveDish(Dish dish, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String imageUrl = storageService.uploadImage(file);
            dish.setImageUrl(imageUrl);
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
            existingDish.setImageUrl(storageService.uploadImage(file));
        }

        dishRepository.save(existingDish);
    }

    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }

    // Proposed additional method
    public List<Dish> getDishesByCategory(Long categoryId) {
        return dishRepository.findByCategoryId(categoryId);
    }
}
