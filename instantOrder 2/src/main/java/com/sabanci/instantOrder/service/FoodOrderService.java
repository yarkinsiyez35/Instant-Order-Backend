package com.sabanci.instantOrder.service;

import java.util.List;
import com.sabanci.instantOrder.model.FoodOrder;


public interface FoodOrderService {
	List<FoodOrder> getFoodOrders();
	FoodOrder findFoodOrderByObjectId(String ObjectId);
	FoodOrder addFoodOrder(FoodOrder foodOrder);
	FoodOrder updateFoodOrder(FoodOrder foodOrder);
	FoodOrder deleteFoodOrder(FoodOrder foodOrder);
}
