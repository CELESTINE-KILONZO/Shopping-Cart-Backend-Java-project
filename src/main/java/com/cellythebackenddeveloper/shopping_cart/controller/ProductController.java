package com.cellythebackenddeveloper.shopping_cart.controller;

import com.cellythebackenddeveloper.shopping_cart.model.Product;
import com.cellythebackenddeveloper.shopping_cart.request.AddProductRequest;
import com.cellythebackenddeveloper.shopping_cart.response.ApiResponse;
import com.cellythebackenddeveloper.shopping_cart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController("{api.prefix}/products")
@RequiredArgsConstructor

public class ProductController {
    private final IProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<ApiResponse> createProduct(AddProductRequest addProductRequest) {
        try {
            Product product = productService.addProduct(addProductRequest);
            return ResponseEntity.ok(new ApiResponse("Product created successfully", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to create product: ", null));
        }
    }

    @GetMapping("/getallproducts")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getProducts();
            return ResponseEntity.ok(new ApiResponse("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to retrieve products: ", null));
        }
    }

    @GetMapping("/getproductbyid")
    public ResponseEntity <ApiResponse> getProductsById(Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Product retrieved successfully", product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("No product found", null));
        }
    }

    @GetMapping
    public ResponseEntity <ApiResponse> getProductsByName(String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            return ResponseEntity.ok(new ApiResponse("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Failed to retrieve products: ", null));
        }
    }
    }
