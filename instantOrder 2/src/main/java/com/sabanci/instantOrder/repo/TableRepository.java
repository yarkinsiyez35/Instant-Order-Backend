package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.FoodOrder;
import com.sabanci.instantOrder.model.FoodOrderTable;
import com.sabanci.instantOrder.model.Table;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TableRepository extends MongoRepository<Table, String> {

    List<Table> findTablesByPaymentReceived(boolean b);
    Optional<Table> findTableByFoodOrders(List<FoodOrderTable> foodOrders);
    Optional<Table> findTableByTableId(int tableId);
    boolean existsTableByTableId(int tableId);


}