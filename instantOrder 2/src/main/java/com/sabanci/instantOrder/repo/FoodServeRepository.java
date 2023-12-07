package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.FoodServe;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

@Document
public interface FoodServeRepository extends MongoRepository<FoodServe, String> {
    Optional<FoodServe> findFoodServeByObjectId(String s);
}
