package com.sabanci.instantOrder.controller;


import com.sabanci.instantOrder.model.FoodOrderServe;
import com.sabanci.instantOrder.model.UpdateMessage;
import com.sabanci.instantOrder.service.FoodOrderServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("instantOrder/serve")
public class ServeRestController {
    FoodOrderServeService foodOrderServeService;

    @Autowired
    ServeRestController(FoodOrderServeService foodOrderServeService1)
    {
        this.foodOrderServeService = foodOrderServeService1;
    }

    @GetMapping()
    List<FoodOrderServe> getFoodOrderServes()
    {
        return foodOrderServeService.findAllFoodOrderServe();
    }

    @GetMapping("/{objectId}")
    ResponseEntity<Object> getFoodOrderServe(@PathVariable String objectId)
    {
        try
        {
            //find FoodOrderServe
            FoodOrderServe searchedFoodOrderServe = foodOrderServeService.findFoodOrderServeByObjectId(objectId);
            //return FoodOrderServe
            return  ResponseEntity.ok(searchedFoodOrderServe);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/{objectId}")
    ResponseEntity<Object> updateFoodOrderServe(@RequestBody UpdateMessage updateMessage,  @PathVariable String objectId)
    {
        try
        {
            FoodOrderServe searchedFoodOrderServe = foodOrderServeService.findFoodOrderServeByObjectId(objectId);
            if(updateMessage.isUpdate())
            {
                //set served to true
                searchedFoodOrderServe.setServed(true);
                //update FoodOrderServe
                searchedFoodOrderServe = foodOrderServeService.updateFoodOrderServe(searchedFoodOrderServe);
                //delete FoodOrderServe
                searchedFoodOrderServe = foodOrderServeService.deleteFoodOrderServe(searchedFoodOrderServe);
            }
            return ResponseEntity.ok(searchedFoodOrderServe);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
