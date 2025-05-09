package com.restaurant.management.repository;

import com.restaurant.management.model.Dish;
import com.restaurant.management.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("SELECT r FROM Recipe r WHERE r.dish.dishId = :dishId")
    List<Recipe> findRecipeByDishId(@Param("dishId") Long dishId);

    void deleteByDish(Dish dish);
}
