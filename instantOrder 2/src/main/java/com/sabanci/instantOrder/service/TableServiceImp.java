package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.FoodOrderTable;
import com.sabanci.instantOrder.model.Table;
import com.sabanci.instantOrder.repo.TableRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
    {
        Optional<Table> searchedTable = tableRepository.findTableByTableId(tableId);
        if (searchedTable.isPresent())
        {
            return searchedTable.get();
        }
        else
        {
            throw new RuntimeException("Table with Id: " + tableId + " does not exist!");
        }
    }

    @Override
    public Table findTableByObjectId(String objectId)
    {
        Optional<Table> searchedTable = tableRepository.findById(objectId);
        if(searchedTable.isPresent())
        {
            return searchedTable.get();
        }
        else
        {
            throw new RuntimeException("Table with objectId: " + objectId + " does not exist!");
        }
    }

    @Override
    public Table addTable(Table table)
    {

        if(tableRepository.existsTableByTableId(table.getTableId()))
        {
            throw new RuntimeException("Table with Id: "+ table.getTableId() + " already exists!");
        }
        return tableRepository.insert(table);
    }

    @Override
    public List<Table> addTables(List<Table> tables)
    {

        List<Table> addedEmployees = new ArrayList<>();
        try
        {
            for (Table toBeAddedTable : tables)
            {
                Table addedTable = addTable(toBeAddedTable);
                addedEmployees.add(addedTable);
            }
        }
        catch(RuntimeException e)
        {
            throw new RuntimeException("Cannot add the table! " + e.getMessage());
        }
        return addedEmployees;
    }

    @Override
    public Table updateTable(Table table)
    {
        if(tableRepository.existsTableByTableId(table.getTableId()))
        {
            return tableRepository.save(table);
        }
        else
        {
            throw new RuntimeException("Table with Id: " + table.getTableId() + " cannot be updated!");
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
        //get FoodOrderTable list
        List<FoodOrderTable> foodOrderTableList = table.getFoodOrders();
        //remove each FoodOrderTable from the list
        foodOrderTableList.clear();
        //set EmployeeId to 0
        table.setEmployeeId(0);
        //set List<FoodOrders> to empty List
        table.setFoodOrders(foodOrderTableList);
        //set total to 0
        table.setTotal(0);
        //set paymentReceived to false
        table.setPaymentReceived(false);
        //update and return the table
        return tableRepository.save(table);
    }
}