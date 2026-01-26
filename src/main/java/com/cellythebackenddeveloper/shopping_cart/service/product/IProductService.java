package com.cellythebackenddeveloper.shopping_cart.service.product;
import com.cellythebackenddeveloper.shopping_cart.dto.ProductDto;
import com.cellythebackenddeveloper.shopping_cart.model.Product;
import com.cellythebackenddeveloper.shopping_cart.request.AddProductRequest;
import com.cellythebackenddeveloper.shopping_cart.request.ProductUpdateRequest;
import java.util.List;

public interface IProductService {

    Product addProduct( AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product , Long id);
    List<Product> getProducts();
    List<Product> getProductsByCategoryName(String name);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByPrice(Double price);
    List<Product> getProductByCategoryNameAndBrand(String category, String brand);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
    List<ProductDto> getConvertedProducts(List<Product> products);
    ProductDto convertToDto(Product product);
}
