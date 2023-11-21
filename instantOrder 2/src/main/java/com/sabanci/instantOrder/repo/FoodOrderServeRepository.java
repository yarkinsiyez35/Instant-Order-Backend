package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.FoodOrder;
import com.sabanci.instantOrder.model.FoodOrderServe;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

@Document
public interface FoodOrderServeRepository extends MongoRepository<FoodOrderServe, String> {


    List<FoodOrderServe> findAllBy();
    Optional<FoodOrderServe> findFoodOrderServesByObjectId(String s);
    List<FoodOrderServe> findFoodOrderServesByFoodOrder(FoodOrder foodOrder);
    List<FoodOrderServe> findFoodOrderServesByServed(boolean b);

}
