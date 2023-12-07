package com.sabanci.instantOrder.controller;



import com.sabanci.instantOrder.model.*;
import com.sabanci.instantOrder.service.CategoryService;
import com.sabanci.instantOrder.service.FoodOrderService;
import com.sabanci.instantOrder.service.TableService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/instantOrder")
public class TableRestController {
    //this controller is responsible for
    //@GET List<Table> --> returns List<Table>
    //@GET Table --> returns Table
    //@POST Table --> creates a new  Table
    //@PUT Table --> clears the attributes in Table
    //@DELETE Table --> deletes an existing Table
    //@GET List<Categories> --> returns List<Category>
    //@GET Category --> returns Category
    //@GET Food --> returns Food
    //@POST Food --> creates a FoodTable, stores FoodTable in Table, creates a FoodOrder

    TableService tableService;
    CategoryService categoryService;
    FoodOrderService foodOrderService;

    @Autowired
    TableRestController(TableService tableService, CategoryService categoryService1, FoodOrderService foodOrderService1){
        this.tableService = tableService;
        this.categoryService = categoryService1;
        this.foodOrderService = foodOrderService1;
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
            //finds the Table
            Table searchedTable = tableService.findTableByTableId(tableId);
            //returns the Table
            return ResponseEntity.status(HttpStatus.OK).body(searchedTable);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/tables/{tableId}")
    public ResponseEntity<Object> addTable(@RequestBody Table table, @PathVariable int tableId)
    {
        try
        {
            //prevents from updating a Table with nonexistent tableId
            table.setTableId(tableId);
            //adds the Table
            Table searchedTable = tableService.addTable(table);
            //returns the Table
            return ResponseEntity.status(HttpStatus.OK).body(searchedTable);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/tables/{tableId}")
    public ResponseEntity<Object> updateTable(@RequestBody UpdateMessage updateMessage , @PathVariable int tableId)
    {
        try
        {
            //finds the searched Table
            Table toBeUpdatedTable = tableService.findTableByTableId(tableId);
            if(updateMessage.isUpdate())    //if bill is paid
            {
                toBeUpdatedTable = tableService.clearTable(toBeUpdatedTable); //clears the Table
            }
            Table updateTable = tableService.updateTable(toBeUpdatedTable); //updates the Table
            //returns the Table
            return ResponseEntity.status(HttpStatus.OK).body(updateTable);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/tables/{tableId}")
    public ResponseEntity<Object> deleteTable(@PathVariable int tableId)
    {
        try
        {
            //finds the searched Table
            Table toBeDeletedTable = tableService.findTableByTableId(tableId);
            //deletes the Table
            Table deletedTable = tableService.deleteTable(toBeDeletedTable);
            //return the Table
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
            //finds the searched Table
            Table searchedTable = tableService.findTableByTableId(tableId);
            //returns every Category
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
            //find Table
            Table searchedTable = tableService.findTableByTableId(tableId);
            //find Category
            Category searchedCategory = categoryService.findCategoryByName(categoryName);
            return ResponseEntity.ok(searchedCategory);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/tables/{tableId}/menu/categories/{categoryName}/{foodName}")
    public ResponseEntity<Object> getFood(@PathVariable int tableId, @PathVariable String categoryName, @PathVariable String foodName)
    {
        try
        {
            //find Table
            Table searchedTable = tableService.findTableByTableId(tableId);
            //find Category
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

    @PostMapping("/tables/{tableId}/menu/categories/{categoryName}/{foodName}")
    public ResponseEntity<Object> createOrder(@PathVariable int tableId, @PathVariable String categoryName, @PathVariable String foodName, @RequestBody NoteAndCount noteAndCount)
    {
        try
        {
            //find table
            Table searchedTable = tableService.findTableByTableId(tableId);
            //find category
            Category searchedCategory = categoryService.findCategoryByName(categoryName);
            //find Food
            Food searchedFood = categoryService.findFoodByCategoryAndFoodName(searchedCategory,foodName);
            //create FoodTable
            FoodTable foodTable = new FoodTable(searchedFood, noteAndCount.getCount(), searchedTable.getTableId());
            //create FoodOrder
            FoodOrder foodOrder = new FoodOrder(searchedFood, searchedTable.getTableId(), noteAndCount.getCount(), noteAndCount.getNote(), false);
            //add FoodTable to Table
            searchedTable.addFoodOrderTable(foodTable);
            //set employeeId
            searchedTable.setEmployeeId(noteAndCount.getEmployeeId());
            //update Table
            searchedTable = tableService.updateTable(searchedTable);
            //add FoodOrder
            foodOrderService.addFoodOrder(foodOrder);
            //return Table
            return ResponseEntity.ok(searchedTable);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}