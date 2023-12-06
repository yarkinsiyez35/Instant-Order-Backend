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
@DependsOn({"foodServiceImp"})      //this is needed to assign to add Food to Category
public class CategoryServiceImp implements CategoryService{

    //what happens if an unexisting Food is direclty added to database? , implement it

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
    public List<Food> findFoodsByCategoryName(String name)
    {   //returns Foods if Category exists by name, throws exception otherwise
        try
        {
            Category searchedCategory = findCategoryByName(name);
            return searchedCategory.getFoods();
        }
        catch(RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Food> findFoodsByCategoryObjectId(String objectId)
    {   //returns Foods if Category exists by objectId, throw exception otherwise
        try
        {
            Category searchedCategory = findCategoryByObjectId(objectId);
            return searchedCategory.getFoods();
        }
        catch(RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Category addFoodToCategoryByObjectId(String objectId, Food food)
    {   //adds Food if Category exists by objectId and returns updated Category, throws exception otherwise
        try
        {
            Category searchedCategory = findCategoryByObjectId(objectId);
            searchedCategory.addFood(food);
            return updateCategory(searchedCategory);
        }
        catch(RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Category addFoodToCategory(Category category, Food food)
    {   //it is assumed that category exists
        //this method will add Food to the given category
        try
        {
            if(!foodRepository.existsFoodByName(food.getName()))    //food.name does not exist in the database
            {
                if(!foodRepository.existsFoodByObjectId(food.getObjectId()))
                {   //food.objectId does not exist in the database --> new Food

                    Food addedFood = foodRepository.save(food); //this assignment is done just in case food.objectId is null
                    category.addFood(addedFood);
                }
                else
                {   //food.objectId exists in the database
                    throw new RuntimeException(food + " has existing objectId but non existing name!");
                }
            }
            else
            {   //food.name exists in the database
                Food existingFood = foodRepository.findFoodByName(food.getName()).get();    //it is guarenteed that food.name exists in the database
                if (existingFood.equals(food))
                {   //food is correct
                    category.addFood(existingFood);
                }
                else
                {   //food input is incorrect, objectId and/or price are different
                    throw new RuntimeException(food + " & " + existingFood + " does not match!");
                }
            }
        }
        catch(RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
        }
        category = categoryRepository.save(category);
        return category;
    }

    @Override
    public Category addFoodsToCategory(Category category, List<Food> foods)
    { //it is assumed that category exists
        //this method will add Foods to given category
        //this method has all or nothing approach
        if (foods.isEmpty())        //prevention against empty list
        {
            throw new RuntimeException("Empty request body!");
        }
        List<Food> foodsToAddToCategory = new ArrayList<>();
        List<Food> foodsToAddToFoodRepository = new ArrayList<>();
        try
        {
            for(Food foodToAdd: foods)      //for each food
            {
                if(!foodRepository.existsFoodByName(foodToAdd.getName()))   //foodName does not exist in the database
                {
                    if(!foodRepository.existsFoodByObjectId(foodToAdd.getObjectId())) //objectId does not exist in the database
                    {   //completely new Food
                        foodsToAddToFoodRepository.add(foodToAdd);
                    }
                    else    //foodName does not exist but objectId exists, wrong input
                    {
                        throw new RuntimeException(foodToAdd + " has existing objectId but nonexistent name!");
                    }
                }
                else
                {   //foodName exists in repository
                    //objectId and price should be checked
                    //this upcoming assignment prevents from adding Food with different price
                    Food existingFoodToAdd = foodRepository.findFoodByName(foodToAdd.getName()).get();  //it is guaranteed that foodName exists in the database
                    if(existingFoodToAdd.equals(foodToAdd)) //if they are the same food
                    {
                        foodsToAddToCategory.add(existingFoodToAdd);
                    }
                    else    //either objectId is wrong or price is wrong
                    {
                        throw new RuntimeException("Price or objectId of " + foodToAdd + " is incorrect!");
                    }
                }
            }
        }
        catch(RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
        }
        //creates Food in foodRepository
        for(Food foodToAddToFoodRepository : foodsToAddToFoodRepository)
        {
            Food savedFood = foodRepository.save(foodToAddToFoodRepository);
            foodsToAddToCategory.add(savedFood);
        }
        //adds Food to category
        for(Food foodtoAddToCategory: foodsToAddToCategory)
        {
            category.addFood(foodtoAddToCategory);
        }
        //adds the category to repository
        category = categoryRepository.save(category);
        //returns the category
        return category;
    }

    @Override
    public Category addFoodToCategoryByName(String name, Food food)
    {   //adds Food if Category exists by name, adds Food to foodRepository if it does not exist, returns updated Category, throws exception otherwise
        try
        {
            Category searchedCategory = findCategoryByName(name);
            searchedCategory.addFood(food);
            if(!foodRepository.existsFoodByObjectId(food.getObjectId()))            //if Food is not in the database
            {
                Food addedFood = foodRepository.save(food);
                searchedCategory.addFood(addedFood);
            }
            return updateCategory(searchedCategory);
        }
        catch(RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
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
    public Food findFoodByCategoryAndFoodObjectId(Category category, String foodObjectId) {
        try
        {
            Optional<Food> searchedFood = foodRepository.findFoodByObjectId(foodObjectId);
            if(searchedFood.isPresent())
            {
                if(category.foodExists(searchedFood.get()))
                {
                    return searchedFood.get();
                }
                else
                {
                    throw new RuntimeException("Food with objectId: " + foodObjectId + " does not exist in Category with name: " + category.getName());
                }
            }
            else
            {
                throw new RuntimeException("Food with objectId: " + foodObjectId + " does not exist");
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
