package com.sabanci.instantOrder.repo;

import com.sabanci.instantOrder.model.Table;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TableRepository extends MongoRepository<Table, String> {
    Optional<Table> findTableByTableId(int tableId);
    boolean existsTableByTableId(int tableId);
}