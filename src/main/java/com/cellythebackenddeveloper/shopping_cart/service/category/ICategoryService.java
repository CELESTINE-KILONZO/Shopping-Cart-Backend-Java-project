package com.cellythebackenddeveloper.shopping_cart.service.category;

import com.cellythebackenddeveloper.shopping_cart.model.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(Category category);
    Category getCategoryById(Long id);
    List <Category> getAllCategories();
    Category updateCategory(Long id, String name);
    void deleteCategory(Long id);
    Category getCategoryByName(String name);
}
