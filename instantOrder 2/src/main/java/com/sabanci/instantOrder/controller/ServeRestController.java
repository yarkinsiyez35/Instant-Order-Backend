package com.sabanci.instantOrder.controller;


import com.sabanci.instantOrder.model.FoodServe;
import com.sabanci.instantOrder.model.UpdateMessage;
import com.sabanci.instantOrder.service.FoodServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("instantOrder/serve")
public class ServeRestController {
    //this controller is responsible for
    //@GET List<FoodServe> --> return List<FoodServe>
    //@GET FoodServe --> return FoodServe
    //@UPDATE FoodServe --> updated FoodServe is deleted from the repository
    //@DELETE is not allowed since not served FoodServe cannot be deleted
    //@POST is implemented in OrderRestController

    FoodServeService foodServeService;

    @Autowired
    ServeRestController(FoodServeService foodServeService1)
    {
        this.foodServeService = foodServeService1;
    }

    @GetMapping()
    List<FoodServe> getFoodServes()
    {
        return foodServeService.findAllFoodServe();
    }

    @GetMapping("/{objectId}")
    ResponseEntity<Object> getFoodServe(@PathVariable String objectId)
    {
        try
        {
            //find FoodServe
            FoodServe searchedFoodServe = foodServeService.findFoodServeByObjectId(objectId);
            //return FoodServe
            return  ResponseEntity.ok(searchedFoodServe);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{objectId}")
    ResponseEntity<Object> updateFoodServe(@RequestBody UpdateMessage updateMessage,  @PathVariable String objectId)
    {
        try
        {
            FoodServe searchedFoodServe = foodServeService.findFoodServeByObjectId(objectId);
            if(updateMessage.isUpdate())
            {
                //set served to true
                searchedFoodServe.setServed(true);
                //update FoodServe
                searchedFoodServe = foodServeService.updateFoodServe(searchedFoodServe);
                //delete FoodServe
                searchedFoodServe = foodServeService.deleteFoodServe(searchedFoodServe);
            }
            return ResponseEntity.ok(searchedFoodServe);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
