package com.sabanci.instantOrder.service;

import java.util.List;
import com.sabanci.instantOrder.model.FoodOrder;


public interface FoodOrderService {
	
    // Retrieves a list of all FoodOrder objects.
	List<FoodOrder> getFoodOrders();

    // Finds a specific FoodOrder by its unique object ID.
	FoodOrder findFoodOrderByObjectId(String ObjectId);

    // Finds a FoodOrder based on a table ID. This could be used in a restaurant scenario where orders are linked to specific tables.
	List<FoodOrder> findFoodOrderByTableId(int tableId);

    // Adds a new FoodOrder to the system.
	FoodOrder addFoodOrder(FoodOrder foodOrder);

    // Updates an existing FoodOrder. This method can handle modifications to an existing order.
	FoodOrder updateFoodOrder(FoodOrder foodOrder);

    // Deletes a specific FoodOrder from the system.
	FoodOrder deleteFoodOrder(FoodOrder foodOrder);

    // Checks if a food item exists by its name. This could be used to validate food items before adding them to an order.
    boolean existsFoodOrderByObjectId(String objectId);
}
