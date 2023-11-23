package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.FoodOrderTable;

import java.util.List;

public interface FoodOrderTableService {

    List<FoodOrderTable> getFoodOrderTables();
    FoodOrderTable getFoodOrderTableByObjectId(String objectId);
    List<FoodOrderTable> getFoodOrderTablesByTableId(int tableId);      //find all FoodOrderTables that belong to the table with tableId


    FoodOrderTable addFoodOrderTable(FoodOrderTable foodOrderTable);
    FoodOrderTable deleteFoodOrderTable(FoodOrderTable foodOrderTable);

}
