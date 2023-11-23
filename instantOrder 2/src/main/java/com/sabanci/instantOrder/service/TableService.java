package com.sabanci.instantOrder.service;


import java.util.List;

import com.sabanci.instantOrder.model.Table;

public interface TableService {

    List<Table> getTables();
    Table findTableByObjectId(String ObjectId);
    Table findTableByTableId(int tableId);
    Table addTable(Table table);
    List <Table> addTables(List <Table> tables);
    Table updateTable(Table table);
    Table deleteTable(Table table);

    Table clearTable(Table table);          //deletes List<FoodOrderTable>, Table.total  = 0, Table.employeeId = 0

}
