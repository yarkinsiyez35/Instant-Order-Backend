package com.sabanci.instantOrder.service;

import com.sabanci.instantOrder.model.Category;
import com.sabanci.instantOrder.model.Food;
import com.sabanci.instantOrder.repo.CategoryRepository;
import com.sabanci.instantOrder.repo.FoodRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

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
    public Category addFoodToCategoryByName(String name, Food food)
    {   //adds Food if Category exists by name and returns updated Category, throws exception otherwise
        try
        {
            Category searchedCategory = findCategoryByName(name);
            searchedCategory.addFood(food);
            return updateCategory(searchedCategory);
        }
        catch(RuntimeException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Category addCategory(Category category)
    {   //adds and returns Category if it has unique name, throws exception otherwise
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
        if (categoryRepository.existsCategoriesByObjectId(category.getObjectId()))
        {
            return categoryRepository.save(category);
        }
        else
        {
            throw new RuntimeException("Category with objectId: " + category.getObjectId() + " does not exist!");
        }
    }

    @Override
    public Category deleteCategory(Category category)
    {   //deletes and returns Category if it exists, throws exception otherwise
        Optional<Category> toBeDeletedCategory = categoryRepository.findCategoryByObjectId(category.getObjectId());
        if(toBeDeletedCategory.isPresent())
        {
            categoryRepository.delete(category);
            return toBeDeletedCategory.get();
        }
        else
        {
            throw new RuntimeException("Category with objectId: " + category.getObjectId() + " cannot be deleted");
        }
    }
}
