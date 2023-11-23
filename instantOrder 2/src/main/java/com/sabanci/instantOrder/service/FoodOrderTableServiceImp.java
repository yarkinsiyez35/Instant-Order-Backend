package com.sabanci.instantOrder.service;


import com.sabanci.instantOrder.model.FoodOrderTable;
import com.sabanci.instantOrder.repo.FoodOrderTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodOrderTableServiceImp implements FoodOrderTableService{

    FoodOrderTableRepository foodOrderTableRepository;

    @Autowired
    FoodOrderTableServiceImp(FoodOrderTableRepository foodOrderTableRepository1)
    {
        this.foodOrderTableRepository = foodOrderTableRepository1;
    }




    @Override
    public List<FoodOrderTable> getFoodOrderTables() {
        return foodOrderTableRepository.findAll();
    }

    @Override
    public FoodOrderTable getFoodOrderTableByObjectId(String objectId) {
        Optional<FoodOrderTable> searchedFoodOrderTable = foodOrderTableRepository.getFoodOrderTableByObjectId(objectId);
        if(searchedFoodOrderTable.isPresent())
        {
            return  searchedFoodOrderTable.get();
        }
        else
        {
            throw new RuntimeException("FoodOrderTable with objectId: " + objectId + " does not exist!");
        }
    }

    @Override
    public List<FoodOrderTable> getFoodOrderTablesByTableId(int tableId) {
        if(foodOrderTableRepository.existsFoodOrderTableByTableId(tableId))
        {
            return foodOrderTableRepository.getFoodOrderTableByTableId(tableId);
        }
        else
        {
            throw new RuntimeException("FoodOrderTable with tableId: " + tableId + " does not exist!");
        }
    }

    @Override
    public FoodOrderTable addFoodOrderTable(FoodOrderTable foodOrderTable) {
        if(foodOrderTableRepository.existsFoodOrderTableByObjectId(foodOrderTable.getObjectId()))
        {
            throw new RuntimeException("FoodOrderTable with objectId: " + foodOrderTable.getObjectId() + " already exist!");
        }
        else
        {
            return foodOrderTableRepository.save(foodOrderTable);
        }
    }

    @Override
    public FoodOrderTable deleteFoodOrderTable(FoodOrderTable foodOrderTable)
    {
        Optional<FoodOrderTable> toBeDeletedFoodOrderTable = foodOrderTableRepository.findById(foodOrderTable.getObjectId()) ;
        if(toBeDeletedFoodOrderTable.isPresent())
        {
            foodOrderTableRepository.delete(toBeDeletedFoodOrderTable.get());
            return toBeDeletedFoodOrderTable.get();
        }
        else
        {
            throw new RuntimeException("FoodOrderTable with objectId: " + foodOrderTable.getObjectId() + "does not exist!");
        }
    }
}
