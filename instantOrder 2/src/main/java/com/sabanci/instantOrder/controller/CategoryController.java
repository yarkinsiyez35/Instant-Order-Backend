package com.sabanci.instantOrder.controller;


import com.sabanci.instantOrder.model.Category;
import com.sabanci.instantOrder.model.Food;
import com.sabanci.instantOrder.service.CategoryService;
import com.sabanci.instantOrder.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/instantOrder/menu")
public class CategoryController
{
    //this controller is responsible for
    //@Get List<Categories> -->returns List<Category>
    //@Get Category -->returns the requested Category
    //@Post Category -->creates a new Category with given List<Food>
    //@Put Category  -->updates an existing Category with given List<Food>
    //@Delete Category  -->deletes an existing Category and its List<Food>

    CategoryService categoryService;
    FoodService foodService;

    @Autowired
    CategoryController(CategoryService categoryService1, FoodService foodService1)
    {
        this.categoryService = categoryService1;
        this.foodService = foodService1;
    }


    @GetMapping("/categories")
    public List<Category> getCategories()
    {
        return categoryService.getCategories();
    }

    @GetMapping("/categories/{categoryName}")
    public ResponseEntity<Object> getCategory(@PathVariable String categoryName)
    {
        try
        {
            Category searchedCategory = categoryService.findCategoryByName(categoryName);
            return ResponseEntity.ok(searchedCategory);
        }
        catch(RuntimeException e)
        {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/categories/{categoryName}/{foodName}")
    public ResponseEntity<Object> getFoodFromCategory(@PathVariable String categoryName, @PathVariable String foodName)
    {
        try
        {
            //find Category
            Category searchedCategory = categoryService.findCategoryByName(categoryName);       //this will throw an exception if categoryName does not exist
            //find Food
            Food searchedFood = foodService.findFoodByName(foodName);                           //this will throw an exception if foodName does not exist

            //find Foods in the Category
            List<Food> foodsInCategory = searchedCategory.getFoods();

            //if that Food is in that category
            if (foodsInCategory.contains(searchedFood))
            {
                return ResponseEntity.ok(foodName);
            }
            else
            {   //Food and Category exist but Food is not in the correct category
                throw new RuntimeException("Food with name: " + foodName + " does not exist in category with name: " + categoryName);
            }
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/categories/{categoryName}/save")
    public ResponseEntity<Object> addCategory(@PathVariable String categoryName, @RequestBody List<Food> foods)
    {
        try
        {
            //creates a Category with empty List<Food>
            Category addedCategory = categoryService.addCategoryByName(categoryName);
            //adds Food to the category
            addedCategory = categoryService.addFoodsToCategory(addedCategory,foods);
            return ResponseEntity.ok(addedCategory);
        }
        catch(RuntimeException e)
        {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/categories/{categoryName}/save")
    public ResponseEntity<Object> updateCategory(@PathVariable String categoryName, @RequestBody List<Food> foods)
    {
        try
        {
            //find the Category
            Category categorytoUpdate = categoryService.findCategoryByName(categoryName);
            //add Foods to the category
            categorytoUpdate = categoryService.addFoodsToCategory(categorytoUpdate,foods);
            //update category
            Category updatedCategory = categoryService.updateCategory(categorytoUpdate);
            return ResponseEntity.ok(updatedCategory);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/categories/{categoryName}/delete")
    public ResponseEntity<Object> deleteCategory(@PathVariable String categoryName)
    {
        try
        {
            Category categoryToDelete = categoryService.findCategoryByName(categoryName);
            Category deletedCategory = categoryService.deleteCategory(categoryToDelete);
            return ResponseEntity.ok(deletedCategory);
        }
        catch (RuntimeException e)
        {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    //Post and Put functionality will be added after implementing adding Food to foodRepository in categoryService






}
