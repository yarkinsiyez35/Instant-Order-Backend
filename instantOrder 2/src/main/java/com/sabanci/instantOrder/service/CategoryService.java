package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.Category;
import com.sabanci.instantOrder.model.Food;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();
    Category findCategoryByObjectId(String objectId);
    Category findCategoryByName(String name);
    List<Food> findFoodsByCategoryName(String name);
    List<Food> findFoodsByCategoryObjectId(String objectId);
    Category addFoodToCategoryByObjectId(String objectId, Food food);
    Category addFoodToCategoryByName(String name, Food food);
    Category addCategory(Category category);
    Category updateCategory(Category category);
    Category deleteCategory(Category category);
}
