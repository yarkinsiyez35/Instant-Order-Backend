package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.Food;
import com.sabanci.instantOrder.repo.FoodRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImp implements FoodService{

    FoodRepository foodRepository;

    @Autowired
    FoodServiceImp(FoodRepository foodRepository1)
    {
        this.foodRepository = foodRepository1;
    }

    @PostConstruct
    public void init()
    {   //adds Foods if database is empty
        if(foodRepository.count() == 0)
        {
            //Foods in Fastfood category
            Food food1 = new Food("Hamburger", 180);
            Food food2 = new Food("Cheeseburger", 200);
            Food food3 = new Food("Hotdog", 140);
            Food food4 = new Food("Baconburger", 210);
            Food food5 = new Food("Chickenburger", 170);


            //add Foods in Pizza Category
            Food food6 = new Food("Pepperoni Pizza", 210);
            Food food7 = new Food("Margharita Pizza", 170);
            Food food8 = new Food("Mixed Pizza", 230);
            Food food9 = new Food("Neapolitan Pizza", 215);
            Food food10 = new Food("Pineapple Pizza", 190);

            //add Foods in Salad Category
            Food food11 = new Food("Caesar Salad", 150);
            Food food12 = new Food("Chicken Salad", 140);
            Food food13 = new Food("Potato Salad", 145);
            Food food14 = new Food("Avocado Salad", 170);
            Food food15 = new Food("Egg Salad", 140);

            //add Foods in Drink Category
            Food food16 = new Food("Coca Cola", 30);
            Food food17 = new Food("Fanta", 30);
            Food food18 = new Food("Water", 10);
            Food food19 = new Food("Orange Juice", 40);
            Food food20 = new Food("Sprite", 30);

            List<Food> foods = new ArrayList<>();
            foods.addAll(List.of(food1, food2,food3, food4,food5,food6,food7,food8, food9, food10, food11, food12
            , food13, food14, food15, food16, food17, food18, food19, food20));
            foodRepository.insert(foods);
        }

    }



    @Override
    public List<Food> getFoods()
    {
        return foodRepository.findAll();
    }

    @Override
    public Food findFoodByObjectId(String objectId)
    {   //returns Food if it exists, throws exception otherwise
        Optional<Food> searchedFood = foodRepository.findFoodByObjectId(objectId);
        if (searchedFood.isPresent())
        {
            return searchedFood.get();
        }
        else
        {
            throw new RuntimeException("Food with objectId: "+ objectId + " does not exist!");
        }
    }

    @Override
    public Food findFoodByName(String name)
    { //returns Food if it exists, throws exception otherwise
        Optional<Food> searchedFood = foodRepository.findFoodByName(name);
        if(searchedFood.isPresent())
        {
            return searchedFood.get();
        }
        else
        {
            throw new RuntimeException("Food with name: "+ name + " does not exist!");
        }
    }

    @Override
    public Food addFood(Food food)
    {   //adds Food if it has a unique name, throws exception otherwise
        if(foodRepository.existsFoodByName(food.getName()))
        {
            throw new RuntimeException("Food with name: " + food.getName() + " already exists!");
        }
        else
        {
            return foodRepository.save(food);
        }
    }

    @Override
    public Food updateFood(Food food)
    {   //updates Food with existing objectId, throws exception otherwise
        if(foodRepository.existsFoodByObjectId(food.getObjectId()))
        {
            return foodRepository.save(food);
        }
        else
        {
            throw new RuntimeException("Food with objectId: " + food.getObjectId() + " does not exist!");
        }
    }

    @Override
    public Food deleteFood(Food food)
    {   //deletes Food with existing objectId, throws exception otherwise
        Optional<Food> foodToBeDeleted = foodRepository.findFoodByObjectId(food.getObjectId());
        if(foodToBeDeleted.isPresent())
        {
            foodRepository.delete(food);
            return foodToBeDeleted.get();
        }
        else
        {
            throw new RuntimeException("Food with objectId: "+ food.getObjectId() + " does not exist!");
        }
    }
}
