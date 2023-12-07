package com.sabanci.instantOrder.controller;


import com.sabanci.instantOrder.model.Food;
import com.sabanci.instantOrder.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instantOrder")
public class FoodRestController {
    //this controller is responsible for:
    //@Get List<Food> --> returns List<Food>
    //@Get Food --> returns Food
    //@Put Food --> updates Food
    //@Delete and @Post methods for Food are implemented in CategoryRestController

    FoodService foodService;
    @Autowired
    FoodRestController(FoodService foodService1)
    {
        this.foodService = foodService1;
    }

    @GetMapping("/foods")
    public List<Food> getFoods()
    {
        return foodService.getFoods();
    }

    @GetMapping("/foods/{name}")
    public ResponseEntity<Object> getFood(@PathVariable String name)
    {
        try
        {
            //find the Food
            Food searchedFood = foodService.findFoodByName(name);
            //return the Food
            return ResponseEntity.status(HttpStatus.OK).body(searchedFood);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/foods/{name}")
    public ResponseEntity<Object> updateFood(@RequestBody Food food, @PathVariable String name)
    {
        try
        {
            //find the Food
            Food searchedFood = foodService.findFoodByName(name);
            //this assignment prevents from changing the objectId
            searchedFood.setName(food.getName());
            searchedFood.setPrice(food.getPrice());
            //update the Food
            Food updatedFood = foodService.updateFood(searchedFood);
            //return the Food
            return ResponseEntity.status(HttpStatus.OK).body(updatedFood);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
        }
    }
}
