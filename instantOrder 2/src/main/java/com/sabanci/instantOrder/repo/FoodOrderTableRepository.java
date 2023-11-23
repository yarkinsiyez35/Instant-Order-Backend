package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.FoodOrderTable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface FoodOrderTableRepository extends MongoRepository<FoodOrderTable, String> {

    Optional<FoodOrderTable> getFoodOrderTableByObjectId(String objectId);      //returns a FoodOrderTable with given objectId
    List<FoodOrderTable> getFoodOrderTableByTableId(int tableId);           //returns List<FoodOrderTable> that belong to tableId
    boolean existsFoodOrderTableByObjectId(String objectId);                //returns true if FoodOrderTable exists
    boolean existsFoodOrderTableByTableId(int tableId);         //returns true if a table has existing FoodOrderTable
}
