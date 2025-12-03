package com.cellythebackenddeveloper.shopping_cart.service.product;

import com.cellythebackenddeveloper.shopping_cart.model.Product;

import java.util.List;

public interface IProductService {

    Product  addProduct( Product product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    void updateProduct(Product product , Long id);
    List<Product> getProducts(Long id);
    List<Product> getProductsByCategoryName(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByPrice(Double price);
    List<Product> getProductByCategoryNameAndBrand(String category, String brand);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
