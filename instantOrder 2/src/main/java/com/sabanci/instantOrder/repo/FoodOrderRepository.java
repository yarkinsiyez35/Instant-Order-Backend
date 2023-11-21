package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.Food;
import com.sabanci.instantOrder.model.FoodOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodOrderRepository extends MongoRepository<FoodOrder, String> {

    List<FoodOrder> findAllBy();
    Optional<FoodOrder> findFoodOrderByObjectId(String s);
    List<FoodOrder> findFoodOrderByCookingStatus(Boolean bool);
    List<FoodOrder> findFoodOrderByTableId(int i);
    List<FoodOrder> findFoodOrderByFood(Food f);


}
