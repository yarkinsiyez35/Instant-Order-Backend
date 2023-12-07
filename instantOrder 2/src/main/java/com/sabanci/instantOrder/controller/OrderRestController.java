package com.sabanci.instantOrder.controller;


import com.sabanci.instantOrder.model.FoodOrder;
import com.sabanci.instantOrder.model.FoodServe;
import com.sabanci.instantOrder.model.UpdateMessage;
import com.sabanci.instantOrder.service.FoodServeService;
import com.sabanci.instantOrder.service.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("instantOrder/orders")
public class OrderRestController {
    //this controller is responsible for
    //@GET List<FoodOrder> --> returns List<FoodOrder>
    //@GET FoodOrder --> returns FoodOrder
    //@PUT FoodOrder --> updates FoodOrder, removes it from the FoodOrder repository, creates a FoodServe
    //@DELETE is not allowed since uncooked FoodOrder cannot be deleted
    //@POST is implemented in TableRestController


    FoodOrderService foodOrderService;
    FoodServeService foodServeService;

    @Autowired
    OrderRestController(FoodOrderService foodOrderService1, FoodServeService foodServeService1)
    {
        this.foodOrderService =foodOrderService1;
        this.foodServeService = foodServeService1;
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
                //create FoodServe
                System.out.println(searchedFoodOrder);
                FoodServe foodServe = new FoodServe(searchedFoodOrder, false);
                //add FoodServe
                System.out.println(foodServe);
                FoodServe addedFoodServe = foodServeService.addFoodServe(foodServe);
            }
            return ResponseEntity.ok(searchedFoodOrder);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
