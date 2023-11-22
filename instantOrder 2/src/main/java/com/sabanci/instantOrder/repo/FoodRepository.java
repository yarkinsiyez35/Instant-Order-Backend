package com.sabanci.instantOrder.repo;


import com.sabanci.instantOrder.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends MongoRepository<Food, String> {

    List<Food> findAllBy();
    Optional<Food> findFoodByObjectId(String objectId);
    Optional<Food> findFoodByName(String name);

    boolean existsFoodByName(String name);
    boolean existsFoodByObjectId(String objectId);
}
