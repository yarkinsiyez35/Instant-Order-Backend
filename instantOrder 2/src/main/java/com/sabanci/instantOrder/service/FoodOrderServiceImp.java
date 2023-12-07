package com.sabanci.instantOrder.service;
import com.sabanci.instantOrder.model.Employee;
import com.sabanci.instantOrder.model.FoodOrder;
import com.sabanci.instantOrder.model.Food;

import com.sabanci.instantOrder.repo.FoodOrderRepository;
import com.sabanci.instantOrder.repo.FoodRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@DependsOn({"foodServiceImp"})
public class FoodOrderServiceImp implements FoodOrderService {
    FoodOrderRepository foodOrderRepository;

    @Autowired
    FoodOrderServiceImp(FoodOrderRepository foodOrderRepository1) {
        this.foodOrderRepository = foodOrderRepository1;
    }

    @Override
    public List<FoodOrder> getFoodOrders()
    {
        return foodOrderRepository.findAll();
    }

    @Override
    public FoodOrder findFoodOrderByObjectId(String objectId)
    { //finds FoodOrder by objectId
        Optional<FoodOrder> searchedFoodOrder = foodOrderRepository.findById(objectId);
        if (searchedFoodOrder.isPresent())
        {
            return searchedFoodOrder.get();
        }
        else
        {
            throw new RuntimeException("Food Order with objectId: " + objectId + " does not exist!");
        }
    }

    @Override
    public FoodOrder addFoodOrder(FoodOrder foodOrder)
    { //adds FoodOrder if it has a unique objectId
        if (foodOrder.getObjectId() != null && foodOrderRepository.existsById(foodOrder.getObjectId()))
        {
            throw new RuntimeException("Food Order with objectId: " + foodOrder.getObjectId() + " already exists!");
        } else
        {
            return foodOrderRepository.save(foodOrder);
        }
    }

    @Override
    public FoodOrder updateFoodOrder(FoodOrder foodOrder)
    {//updates FoodOrder with existing objectId, throws exception otherwise
        if (foodOrder.hasNull()) //protection against empty bodies
        {
            throw new RuntimeException("Food Order with ID: " + foodOrder.getObjectId() + " has null values!");
        }
        if (foodOrderRepository.existsById(foodOrder.getObjectId()))
        {
            return foodOrderRepository.save(foodOrder);
        }
        else
        {
            throw new RuntimeException("Food Order with objectID: " + foodOrder.getObjectId() + " cannot be updated!");
        }
    }

    @Override
    public FoodOrder deleteFoodOrder(FoodOrder foodOrder)
    {   //deletes FoodOrder with existing objectId, throws exception otherwise
        Optional<FoodOrder> toBeDeletedFoodOrder = foodOrderRepository.findById(foodOrder.getObjectId());
        if (toBeDeletedFoodOrder.isPresent()) {
            foodOrderRepository.delete(foodOrder);
            return toBeDeletedFoodOrder.get();
        }
        else
        {
            throw new RuntimeException(("Food Order with objectId: " + foodOrder.getObjectId() + " cannot be deleted!"));
        }
    }

}
