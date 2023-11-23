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
    //@Get List<Food>
    //@Get Food
    //@Put Food


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

    @GetMapping("/foods/{objectId}")
    public ResponseEntity<Object> getFood(@PathVariable String objectId)
    {
        try
        {
            Food searchedFood = foodService.findFoodByObjectId(objectId);
            return ResponseEntity.status(HttpStatus.OK).body(searchedFood);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/foods/save")
    public ResponseEntity<Object> updateFood(@RequestBody Food food)
    {
        try
        {
            Food searchedFood = foodService.updateFood(food);
            return ResponseEntity.status(HttpStatus.OK).body(searchedFood);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
