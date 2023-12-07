package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.FoodOrder;
import com.sabanci.instantOrder.model.FoodServe;

import java.util.List;

public interface FoodServeService {

    List<FoodServe> findAllFoodServe();
    FoodServe findFoodServeByObjectId(String objectId);
    FoodServe addFoodServe(FoodServe foodServe);
    FoodServe updateFoodServe(FoodServe foodServe);
    FoodServe deleteFoodServe(FoodServe foodServe);
    
}
