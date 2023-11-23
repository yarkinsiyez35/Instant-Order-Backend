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
    Category addFoodToCategory(Category category, Food food);
    Category addFoodsToCategory(Category category, List<Food> foods);
    Category addFoodToCategoryByName(String name, Food food);
    Category addCategory(Category category);
    Category addCategoryByName(String categoryName);
    Category updateCategory(Category category);
    Category deleteCategory(Category category);
}
