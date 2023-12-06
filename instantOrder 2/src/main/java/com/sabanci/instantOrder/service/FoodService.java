package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.Food;

import java.util.List;

public interface FoodService {
    List<Food> getFoods();
    Food findFoodByName(String name);
    Food addFood(Food food);
    Food updateFood(Food food);
    Food deleteFood(Food food);

    boolean existsFoodByName(String name);

}
