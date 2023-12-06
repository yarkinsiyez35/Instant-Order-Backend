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
    //@Get List<Categories> --> returns List<Category>
    //@Get Category --> returns the requested Category
    //@Post Category --> creates a new Category with given List<Food>
    //@Put Category  --> updates an existing Category with given List<Food>
    //@Delete Category  --> deletes an existing Category and its List<Food>
    //@Delete Food --> deletes an existing Food in that Category, if updated Category has no remaining Food, Category will be deleted

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
            //find the category
            Category searchedCategory = categoryService.findCategoryByName(categoryName);
            //return the category
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

    @PostMapping("/categories/{categoryName}")
    public ResponseEntity<Object> addCategory(@PathVariable String categoryName, @RequestBody Food food)
    {
        boolean foodExists = true;
        try
        {
            //creates the category Object
            Category newCategory = new Category(categoryName);
            //check if food exists
            if(foodService.existsFoodByName(food.getName()))    //Food exists
            {
                Food searchedFood = foodService.findFoodByName(food.getName()); //find the searched Food
                newCategory.addFood(searchedFood);  //add Food to Category
            }
            else    //Food does not exist in the database
            {
                Food addedFood = foodService.addFood(food); //add Food to repository
                newCategory.addFood(addedFood); //add Food to Category
                foodExists = false;
            }
            Category addedCategory = categoryService.addCategory(newCategory);
            return ResponseEntity.ok(addedCategory);
        }
        catch(RuntimeException e)
        {
            if(!foodExists && foodService.existsFoodByName(food.getName())) //prevention against adding a Food to database and having an error afterward
            {
                Food deletedFood = foodService.deleteFood(foodService.findFoodByName(food.getName()));  //deletes the food
            }
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
        }
    }

    @PutMapping("/categories/{categoryName}")
    public ResponseEntity<Object> updateCategory(@PathVariable String categoryName, @RequestBody Food food)
    {
        boolean foodExists = true;
        try
        {
            //find the Category
            Category categorytoUpdate = categoryService.findCategoryByName(categoryName);
            //check if Food exists
            if(foodService.existsFoodByName(food.getName())) //Food exists in the repository
            {
                Food searchedFood = foodService.findFoodByName(food.getName());
                categorytoUpdate.addFood(searchedFood);
            }
            else //Food does not exist in the repository
            {
                Food addedFood = foodService.addFood(food); //add Food to repository
                categorytoUpdate.addFood(addedFood); //add Food to Category
                foodExists = false;
            }
            //update the Category
            Category updatedCategory = categoryService.updateCategory(categorytoUpdate);
            //return the Category
            return ResponseEntity.ok(updatedCategory);
        }
        catch (RuntimeException e)
        {
            if(!foodExists && foodService.existsFoodByName(food.getName())) //prevention against adding Food to database and having an error afterward
            {
                Food deletedFood = foodService.deleteFood(foodService.findFoodByName(food.getName()));  //deletes the food
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/categories/{categoryName}")
    public ResponseEntity<Object> deleteCategory(@PathVariable String categoryName)
    {
        try
        {
            //find the Category
            Category categoryToDelete = categoryService.findCategoryByName(categoryName);
            //delete the Category
            Category deletedCategory = categoryService.deleteCategory(categoryToDelete);
            //return the Category
            return ResponseEntity.ok(deletedCategory);
        }
        catch (RuntimeException e)
        {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/categories/{categoryName}/{foodName}")
    public ResponseEntity<Object> deleteFoodFromCategory(@PathVariable String categoryName, @PathVariable String foodName)
    {
        try
        {
            //find the Category
            Category searchedCategory = categoryService.findCategoryByName(categoryName);
            //find the Food
            Food foodToDelete = categoryService.findFoodByCategoryAndFoodName(searchedCategory,foodName);
            //delete the food
            Food deletedFood = foodService.deleteFood(foodToDelete);

            Category updatedCategory = categoryService.findCategoryByObjectId(searchedCategory.getObjectId());
            if(updatedCategory.hasNull()) //if category becomes empty
            {
                updatedCategory = categoryService.deleteCategory(updatedCategory); //deletes the category
            }
            //return the category
            return ResponseEntity.ok(updatedCategory);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
