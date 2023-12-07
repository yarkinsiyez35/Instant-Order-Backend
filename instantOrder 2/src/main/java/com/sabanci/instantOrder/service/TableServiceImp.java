package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.FoodTable;
import com.sabanci.instantOrder.model.Table;
import com.sabanci.instantOrder.repo.TableRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TableServiceImp implements TableService{

    TableRepository tableRepository;

    @Autowired
    TableServiceImp(TableRepository tableRepository1)
    {
        this.tableRepository = tableRepository1;
    }

    @PostConstruct
    public void init()
    {
        if(tableRepository.count() == 0)
        {
            int[] newTableIds = {1, 2, 3, 4, 5, 6, 7, 8};

            for (int tableId : newTableIds) {

                Table newTable = new Table(tableId);
                tableRepository.save(newTable);

            }
        }
    }

    @Override
    public List<Table> getTables()
    {
        return tableRepository.findAll();
    }

    @Override
    public Table findTableByTableId(int tableId)
    { //finds Table by tableId, throws exception otherwise
        Optional<Table> searchedTable = tableRepository.findTableByTableId(tableId);
        if (searchedTable.isPresent())
        {
            return searchedTable.get();
        }
        else
        {
            throw new RuntimeException("Table with tableId: " + tableId + " does not exist!");
        }
    }

    @Override
    public Table addTable(Table table)
    { //adds a Table if it has a unique tableId, throws exception otherwise
        if(tableRepository.existsTableByTableId(table.getTableId()))
        {
            throw new RuntimeException("Table with tableId: "+ table.getTableId() + " already exists!");
        }
        return tableRepository.insert(table);
    }

    @Override
    public Table updateTable(Table table)
    { //updates a Table if it has a unique tableId, throws exception otherwise
        if(tableRepository.existsTableByTableId(table.getTableId()))
        {
            return tableRepository.save(table);
        }
        else
        {
            throw new RuntimeException("Table with tableId: " + table.getTableId() + " cannot be updated!");
        }
    }

    @Override
    public Table deleteTable(Table table)
    {
        Optional<Table> toBeDeletedTable = tableRepository.findById(table.getObjectId());
        if(toBeDeletedTable.isPresent())
        {
            tableRepository.delete(table);
            return toBeDeletedTable.get();
        }
        else
        {
            throw new RuntimeException(("Table with objectId: " + table.getObjectId() + " cannot be deleted!"));
        }
    }

    @Override
    public Table clearTable(Table table)
    {   //it is assumed that Table is existing
        //this method resets every value of Table except tableId to default
        //get FoodTable list
        List<FoodTable> foodTableList = table.getFoodOrders();
        //remove each FoodTable from the list
        foodTableList.clear();
        //set EmployeeId to 0
        table.setEmployeeId(0);
        //set List<FoodOrders> to empty List
        table.setFoodOrders(foodTableList);
        //set total to 0
        table.setTotal(0);
        //set paymentReceived to false
        table.setPaymentReceived(false);
        //update and return the table
        return tableRepository.save(table);
    }
}