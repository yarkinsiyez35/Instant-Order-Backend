package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.Category;
import com.sabanci.instantOrder.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {

    List<Category> findAllBy();
    Optional<Category> findCategoryByObjectId(String objectId);
    Optional<Category> findCategoryByName(String name);

    boolean existsCategoriesByName(String name);
    boolean existsCategoriesByObjectId(String objectId);




}
