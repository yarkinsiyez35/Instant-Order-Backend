package com.sabanci.instantOrder.controller;


import com.sabanci.instantOrder.model.FoodOrder;
import com.sabanci.instantOrder.model.FoodOrderServe;
import com.sabanci.instantOrder.model.UpdateMessage;
import com.sabanci.instantOrder.service.FoodOrderServeService;
import com.sabanci.instantOrder.service.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("instantOrder/orders")
public class OrderRestController {
    FoodOrderService foodOrderService;
    FoodOrderServeService foodOrderServeService;

    @Autowired
    OrderRestController(FoodOrderService foodOrderService1, FoodOrderServeService foodOrderServeService1)
    {
        this.foodOrderService =foodOrderService1;
        this.foodOrderServeService = foodOrderServeService1;
    }


    @GetMapping()
    public List<FoodOrder> getFoodOrders()
    {
        return foodOrderService.getFoodOrders();
    }

    @GetMapping("/{objectId}")
    public ResponseEntity<Object> getFoodOrder(@PathVariable String objectId)
    {
        try
        {
            //find FoodOrder
            FoodOrder searchedFoodOrder = foodOrderService.findFoodOrderByObjectId(objectId);
            //return FoodOrder
            return ResponseEntity.ok(searchedFoodOrder);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{objectId}")
    public ResponseEntity<Object> updateFoodOrder(@RequestBody UpdateMessage updateMessage, @PathVariable String objectId)
    {
        try {
            FoodOrder searchedFoodOrder = foodOrderService.findFoodOrderByObjectId(objectId);
            if (updateMessage.isUpdate())
            {
                //set status to true
                searchedFoodOrder.setCookingStatus(true);
                //update FoodOrder
                searchedFoodOrder = foodOrderService.updateFoodOrder(searchedFoodOrder);
                //delete FoodOrder
                searchedFoodOrder = foodOrderService.deleteFoodOrder(searchedFoodOrder);
                //create FoodOrderServe
                System.out.println(searchedFoodOrder);
                FoodOrderServe foodOrderServe = new FoodOrderServe(searchedFoodOrder, false);
                //add FoodOrderServe
                System.out.println(foodOrderServe);
                FoodOrderServe addedFoodOrderServe = foodOrderServeService.addFoodOrderServe(foodOrderServe);
            }
            return ResponseEntity.ok(searchedFoodOrder);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
