package com.sabanci.instantOrder.controller;


import com.sabanci.instantOrder.model.Category;
import com.sabanci.instantOrder.service.CategoryService;
import com.sabanci.instantOrder.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/instantOrder")
public class CategoryController
{
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

    //Post and Put functionality will be added after implementing adding Food to foodRepository in categoryService






}
