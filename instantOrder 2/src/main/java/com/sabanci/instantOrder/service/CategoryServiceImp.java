package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.Category;
import com.sabanci.instantOrder.model.Food;
import com.sabanci.instantOrder.repo.CategoryRepository;
import com.sabanci.instantOrder.repo.FoodRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@DependsOn({"foodServiceImp"})
public class CategoryServiceImp implements CategoryService{

    CategoryRepository categoryRepository;
    FoodRepository foodRepository;          //necessary for reaching Foods

    @Autowired
    CategoryServiceImp(CategoryRepository categoryRepository1, FoodRepository foodRepository1)
    {
        this.categoryRepository = categoryRepository1;
        this.foodRepository = foodRepository1;
    }
    
    @PostConstruct
    public void init()
    {
        if(categoryRepository.count() == 0)     //if category repository is empty
        {

            List<Food> foods = foodRepository.findAll();

            List<Food> FastFoods = foods.subList(0,5);          //first five foods are FastFoods
            Category category1 = new Category("Fast Food", FastFoods);

            List<Food> Pizzas = foods.subList(5,10);            //second five foods are Pizzas
            Category category2 = new Category("Pizza", Pizzas);

            List<Food> Salads = foods.subList(10,15);           //third five foods are Salads
            Category category3 = new Category("Salad", Salads);

            List<Food> Drinks = foods.subList(15,20);           ////fourth five foods are Drinks
            Category category4 = new Category("Drink", Drinks);

            List<Category> categories = categoryRepository.insert(List.of(category1,category2,category3,category4));
        }
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryByObjectId(String objectId)
    {   //returns Category if it exists, throws exception otherwise
        Optional<Category> searchedCategory = categoryRepository.findCategoryByObjectId(objectId);
        if (searchedCategory.isPresent())
        {
            return searchedCategory.get();
        }
        else
        {
            throw new RuntimeException("Category with objectId: " + objectId + " does not exist!");
        }
    }

    @Override
    public Category findCategoryByName(String name)
    {   //returns Category if it exists, throws exception otherwise
        Optional<Category> searchedCategory = categoryRepository.findCategoryByName(name);
        if (searchedCategory.isPresent())
        {
            return searchedCategory.get();
        }
        else
        {
            throw new RuntimeException("Category with name: " + name + " does not exist!");
        }
    }

    @Override
    public Food findFoodByCategoryAndFoodName(Category category, String foodName)
    {   //it is assumed that Category exists
        try
        {
            Optional<Food> searchedFood = foodRepository.findFoodByName(foodName);
            if(searchedFood.isPresent())
            {
                if(category.foodExists(searchedFood.get()))
                {
                    return searchedFood.get();
                }
                else
                {
                    throw new RuntimeException("Food with name: " + foodName + " does not exist in Category with name: " + category.getName());
                }
            }
            else
            {
                throw new RuntimeException("Food with name: " + foodName + " does not exist");
            }
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Category addCategory(Category category)
    {   //adds and returns Category if it has unique name, throws exception otherwise
        if(category.hasNull()) //protection against empty bodies
        {
            throw new RuntimeException("Category with name: " + category.getName() + " has null values!");
        }
        if(categoryRepository.existsCategoriesByName(category.getName()))
        {
            throw new RuntimeException("Category with name: " + category.getName() + " already exists!");
        }
        else
        {
            return categoryRepository.save(category);
        }
    }

    @Override
    public Category updateCategory(Category category)
    {   //updates and returns Category if it exists, throws exception otherwise
        if(category.hasNull()) //protection against empty bodies
        {
            throw new RuntimeException("Category with name: " + category.getName() + " has null values!");
        }
        if (categoryRepository.existsCategoriesByObjectId(category.getObjectId()))
        {
            return categoryRepository.save(category);
        }
        else
        {
            throw new RuntimeException("Category with objectId: " + category.getObjectId() + " cannot be updated!");
        }
    }
    @Override
    public Category deleteCategory(Category category)
    {   //deletes and returns Category if it exists, throws exception otherwise
        Optional<Category> toBeDeletedCategory = categoryRepository.findCategoryByObjectId(category.getObjectId());
        if(toBeDeletedCategory.isPresent())
        {
            //get foods in category
            List<Food> foods = category.getFoods();
            //delete foods in category
            foodRepository.deleteAll(foods);
            //delete category
            categoryRepository.delete(category);
            return toBeDeletedCategory.get();
        }
        else
        {
            throw new RuntimeException("Category with objectId: " + category.getObjectId() + " cannot be deleted!");
        }
    }
}
