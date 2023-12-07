package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.FoodOrder;
import com.sabanci.instantOrder.model.FoodOrderServe;
import java.util.List;

public interface FoodOrderServeService {

    // Method to find and return all FoodOrderServe objects.
    List<FoodOrderServe> findAllFoodOrderServe();

    // Method to find a specific FoodOrderServe by its object ID. It returns an Optional, which may contain a FoodOrderServe or be empty.
    FoodOrderServe findFoodOrderServeByObjectId(String objectId);

    // Method to find FoodOrderServe objects associated with a specific FoodOrder.
    FoodOrderServe findFoodOrderServeByFoodOrder(FoodOrder foodOrder);

    // Method to find FoodOrderServe objects based on their served status (true or false).
    List<FoodOrderServe> findFoodOrdersByServed(boolean served);

    // Method to save a FoodOrderServe object. This can be used for both creating a new record and updating an existing one.
    FoodOrderServe addFoodOrderServe(FoodOrderServe foodOrderServe);
    FoodOrderServe updateFoodOrderServe(FoodOrderServe foodOrderServe);

    // Method to delete a FoodOrderServe object based on its object ID.
    FoodOrderServe deleteFoodOrderServe(FoodOrderServe foodOrderServe);
    
}
