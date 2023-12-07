package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.FoodOrder;
import com.sabanci.instantOrder.model.FoodServe;
import com.sabanci.instantOrder.repo.FoodServeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServeServiceImp implements FoodServeService {

    private FoodServeRepository foodServeRepository;
    @Autowired
    FoodServeServiceImp(FoodServeRepository foodServeRepository1)
    {
        this.foodServeRepository = foodServeRepository1;
    }

    @Override
    public List<FoodServe> findAllFoodServe()
    {
        return foodServeRepository.findAll();
    }

    @Override
    public FoodServe findFoodServeByObjectId(String objectId)
    {   //finds FoodServe by objectId throws exception otherwise
        Optional<FoodServe> searchedFoodServe = foodServeRepository.findFoodServeByObjectId(objectId);
        if (searchedFoodServe.isPresent())
        {
            return searchedFoodServe.get();
        }
        else
        {
            throw new RuntimeException("FoodServe with objectId: " + objectId + " does not exist!");
        }
    }

    @Override
    public FoodServe addFoodServe(FoodServe foodServe)
    { //adds FoodServe if it has unique objectId, throws exception otherwise
        if (foodServe.getObjectId() != null && foodServeRepository.existsById(foodServe.getObjectId()))
        {
            throw new RuntimeException("FoodServe with objectId: " + foodServe.getObjectId() + " already exists!");
        }
        else
        {
            return foodServeRepository.save(foodServe);
        }
    }

    @Override
    public FoodServe updateFoodServe(FoodServe foodServe)
    { //updates FoodServe if it exists, throws exception otherwise
        if(foodServeRepository.existsById(foodServe.getObjectId()))
        {
            return foodServeRepository.save(foodServe);
        }
        else
        {
            throw new RuntimeException("FoodServe with objectId: " + foodServe.getObjectId() + " cannot be updated!");
        }
    }

    @Override
    public FoodServe deleteFoodServe(FoodServe foodServe)
    {   //deletes FoodServe with existing objectId, throws exception otherwise
        Optional<FoodServe> toBeDeletedFoodServe = foodServeRepository.findById(foodServe.getObjectId());
        if(toBeDeletedFoodServe.isPresent())
        {
            foodServeRepository.delete(foodServe);
            return toBeDeletedFoodServe.get();
        }
        else
        {
            throw new RuntimeException(("FoodServe with objectId: " + foodServe.getObjectId() + " cannot be deleted!"));
        }
    }
}
