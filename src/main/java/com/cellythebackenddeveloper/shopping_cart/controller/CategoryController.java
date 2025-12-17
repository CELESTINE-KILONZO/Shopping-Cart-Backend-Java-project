package com.cellythebackenddeveloper.shopping_cart.controller;
import com.cellythebackenddeveloper.shopping_cart.exceptions.AlreadyExistException;
import com.cellythebackenddeveloper.shopping_cart.model.Category;
import com.cellythebackenddeveloper.shopping_cart.response.ApiResponse;
import com.cellythebackenddeveloper.shopping_cart.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix/}categories")

public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category name) {
        try {
            Category category = categoryService.createCategory(name);
            return ResponseEntity.ok(new ApiResponse( "Category created successfully" , category));
        } catch (AlreadyExistException    e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse( "Categories retrieved successfully" , categories));
        } catch (Exception e) {
         return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR:" , INTERNAL_SERVER_ERROR ));
        }
    }
}
