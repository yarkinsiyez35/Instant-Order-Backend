package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.Employee;
import com.sabanci.instantOrder.model.FoodOrder;
import com.sabanci.instantOrder.model.FoodOrderServe;
import com.sabanci.instantOrder.repo.FoodOrderServeRepository;
import com.sabanci.instantOrder.service.FoodOrderServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodOrderServeServiceImp implements FoodOrderServeService {

    @Autowired
    private FoodOrderServeRepository foodOrderServeRepository;

    // Fetches all FoodOrderServe entries from the repository. Throws an exception if no orders are found.
    @Override
    public List<FoodOrderServe> findAllFoodOrderServe() {
        List<FoodOrderServe> orders = foodOrderServeRepository.findAll();
        if (orders.isEmpty()) {
            throw new RuntimeException("No FoodOrderServe found.");
        }
        return orders;
    }

    // Retrieves a specific FoodOrderServe by its object ID. Throws an exception if the order is not found.
    @Override
    public FoodOrderServe findFoodOrderServeByObjectId(String objectId) {

            Optional<FoodOrderServe> searchedFoodOrderServe = foodOrderServeRepository.findFoodOrderServeByObjectId(objectId);
            if (searchedFoodOrderServe.isPresent())
            {
                return searchedFoodOrderServe.get();
            }
            else
            {
                throw new RuntimeException("FoodOrderServe with ID: " + objectId + " does not exist!");
            }
        }

    // Finds all FoodOrderServe entries linked to a specific FoodOrder. Throws an exception if no orders are found for that FoodOrder.
    @Override
    public FoodOrderServe findFoodOrderServeByFoodOrder(FoodOrder foodOrder) {

        Optional<FoodOrderServe> searchedFoodOrderServe = foodOrderServeRepository.findFoodOrderServeByFoodOrder(foodOrder);
        if (searchedFoodOrderServe.isPresent())
        {
            return searchedFoodOrderServe.get();
        }
        else
        {
            throw new RuntimeException("FoodOrderServe with food order: " + foodOrder + " does not exist!");
        }
    }



    // Retrieves all FoodOrderServe entries based on their served status. Throws an exception if no orders match the given status.
    @Override
    public List<FoodOrderServe> findFoodOrdersByServed(boolean served) {

        List<FoodOrderServe> searchedFoodOrderServe = foodOrderServeRepository.findFoodOrderServesByServed(served);
        if (!searchedFoodOrderServe.isEmpty())
        {
            return searchedFoodOrderServe;
        }
        else
        {
            throw new RuntimeException("FoodOrderServe with food order served: " + served + " does not exist!");
        }
    }

    // Saves a FoodOrderServe to the repository. Throws an exception if the provided FoodOrderServe is null.
    @Override
    public FoodOrderServe addFoodOrderServe(FoodOrderServe foodOrderServe)
    {
        if (foodOrderServe.getObjectId() != null && foodOrderServeRepository.existsById(foodOrderServe.getObjectId()))
        {
            throw new RuntimeException("FoodOrderServe with objectId: " + foodOrderServe.getObjectId() + " already exists!");
        }
        return foodOrderServeRepository.save(foodOrderServe);
    }

    @Override
    public FoodOrderServe updateFoodOrderServe(FoodOrderServe foodOrderServe)
    {
        if(foodOrderServeRepository.existsById(foodOrderServe.getObjectId()))
        {
            return foodOrderServeRepository.save(foodOrderServe);
        }
        else
        {
            throw new RuntimeException("FoodOrderServe with objectId: " + foodOrderServe.getObjectId() + " cannot be updated!");
        }
    }

    // Deletes a FoodOrderServe by its object ID. Throws an exception if no order is found with that ID.
    @Override
    public FoodOrderServe deleteFoodOrderServe(FoodOrderServe foodOrderServe)
    {   //deletes Employee with existing objectId, throws exception otherwise
        Optional<FoodOrderServe> toBeDeletedFoodOrderServe = foodOrderServeRepository.findById(foodOrderServe.getObjectId());
        if(toBeDeletedFoodOrderServe.isPresent())
        {
            foodOrderServeRepository.delete(foodOrderServe);
            return toBeDeletedFoodOrderServe.get();
        }
        else
        {
            throw new RuntimeException(("FoodOrderServe with objectId: " + foodOrderServe.getObjectId() + " cannot be deleted!"));
        }
    }
}
