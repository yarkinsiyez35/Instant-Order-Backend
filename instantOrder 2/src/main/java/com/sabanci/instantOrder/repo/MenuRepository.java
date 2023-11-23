package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.Category;
import com.sabanci.instantOrder.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {




    Optional<Category> findByCategories(Category category);



}
