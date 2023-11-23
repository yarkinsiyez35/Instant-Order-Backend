package com.sabanci.instantOrder.controller;



import com.sabanci.instantOrder.model.*;
import com.sabanci.instantOrder.service.CategoryService;
import com.sabanci.instantOrder.service.FoodOrderTableService;
import com.sabanci.instantOrder.service.TableService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/instantOrder")
public class TableRestController {

    TableService tableService;
    CategoryService categoryService;
    FoodOrderTableService foodOrderTableService;

    @Autowired
    TableRestController(TableService tableService, CategoryService categoryService1, FoodOrderTableService foodOrderTableService1){
        this.tableService = tableService;
        this.categoryService = categoryService1;
        this.foodOrderTableService = foodOrderTableService1;
    }


    @GetMapping("/tables")
    public List<Table> getTable()
    {
        return tableService.getTables();
    }

    @GetMapping("/tables/{tableId}")
    public ResponseEntity<Object> getTable(@PathVariable int tableId)
    {
        try
        {
            Table searchedTable = tableService.findTableByTableId(tableId);
            return ResponseEntity.status(HttpStatus.OK).body(searchedTable);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/tables/save")
    public ResponseEntity<Object> addTable(@RequestBody Table table)
    {
        try
        {
            Table searchedTable = tableService.addTable(table);
            return ResponseEntity.status(HttpStatus.OK).body(searchedTable);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/tables/save")
    public ResponseEntity<Object> updateTable(@RequestBody Table table)
    {
        try
        {
            Table searchedTable = tableService.updateTable(table);
            return ResponseEntity.status(HttpStatus.OK).body(searchedTable);
        }

        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/tables/delete")
    public ResponseEntity<Object> deleteTable(@RequestBody Table table)
    {
        try
        {
            Table deletedTable = tableService.deleteTable(table);
            return ResponseEntity.status(HttpStatus.OK).body(deletedTable);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @GetMapping("/tables/{tableId}/menu/categories")
    public List<Category> getCategories(@PathVariable int tableId)
    {
        try
        {
            Table searchedTable = tableService.findTableByTableId(tableId);
            return categoryService.getCategories();
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/tables/{tableId}/menu/categories/{categoryName}")
    public ResponseEntity<Object> getCategory(@PathVariable int tableId, @PathVariable String categoryName)
    {
        try
        {
            //find table
            Table searchedTable = tableService.findTableByTableId(tableId);
            //find category
            Category searchedCategory = categoryService.findCategoryByName(categoryName);
            return ResponseEntity.ok(searchedCategory);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    //this method can have Food.objectId instead of Food.name??
    @GetMapping("/tables/{tableId}/menu/categories/{categoryName}/{foodName}")
    public ResponseEntity<Object> getFood(@PathVariable int tableId, @PathVariable String categoryName, @PathVariable String foodName)
    {
        try
        {
            //find table
            Table searchedTable = tableService.findTableByTableId(tableId);
            //find category
            Category searchedCategory = categoryService.findCategoryByName(categoryName);
            //find Food
            Food searchedFood = categoryService.findFoodByCategoryAndFoodName(searchedCategory,foodName);
            return ResponseEntity.ok(searchedFood);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    //Post method will create a FoodOrderTable, and FoodOrder and send it to the respective databases
    @PostMapping("/tables/{tableId}/menu/categories/{categoryName}/{foodObjectId}")
    public ResponseEntity<Object> createOrder(@PathVariable int tableId, @PathVariable String categoryName, @PathVariable String foodObjectId, @RequestBody NoteAndCount noteAndCount)
    {
        try
        {
            //find table
            Table searchedTable = tableService.findTableByTableId(tableId);
            //find category
            Category searchedCategory = categoryService.findCategoryByName(categoryName);
            //find Food
            Food searchedFood = categoryService.findFoodByCategoryAndFoodObjectId(searchedCategory,foodObjectId);

            //create FoodOrderTable
            FoodOrderTable foodOrderTable = new FoodOrderTable(searchedFood, noteAndCount.getCount(), searchedTable.getTableId());
            //save foodOrderTable
            foodOrderTableService.addFoodOrderTable(foodOrderTable);
            //create FoodOrder
            FoodOrder foodOrder = new FoodOrder(searchedFood, searchedTable.getTableId(), noteAndCount.getCount(), noteAndCount.getNote(), false);
            //add FoodOrderTable to Table
            searchedTable.addFoodOrderTable(foodOrderTable);
            //update Table
            searchedTable = tableService.updateTable(searchedTable);

            //foodOrderService.addFoodOrder(foodOrder);
            return ResponseEntity.ok(foodOrderTable);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    //how to delete?



}

