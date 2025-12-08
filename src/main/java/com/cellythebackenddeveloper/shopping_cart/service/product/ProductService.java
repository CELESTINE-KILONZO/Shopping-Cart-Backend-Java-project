package com.cellythebackenddeveloper.shopping_cart.service.product;
import com.cellythebackenddeveloper.shopping_cart.exceptions.ProductNotFoundException;
import com.cellythebackenddeveloper.shopping_cart.model.Category;
import com.cellythebackenddeveloper.shopping_cart.model.Product;
import com.cellythebackenddeveloper.shopping_cart.repository.CategoryRepository;
import com.cellythebackenddeveloper.shopping_cart.repository.ProductRepository;
import com.cellythebackenddeveloper.shopping_cart.request.AddProductRequest;
import com.cellythebackenddeveloper.shopping_cart.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductService implements IProductService {
    private  final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest addProductRequest) {

        Category category  = Optional.ofNullable(categoryRepository.findByName(addProductRequest.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(addProductRequest.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        addProductRequest.setCategory(category);
        return productRepository.save(createProduct(addProductRequest, category));
    }

    private Product createProduct(AddProductRequest addProductRequest, Category category) {
        return  new Product(
                addProductRequest.getName(),
                addProductRequest.getBrand(),
                addProductRequest.getPrice(),
                addProductRequest.getInventory(),
                addProductRequest.getDescription(),
                category
        );
    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow (()->new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse (productRepository::delete,()->new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest productUpdateRequest, Long id) {
      return productRepository.findById(id)
              .map(existingProduct -> updateExistingProduct( productUpdateRequest , existingProduct))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: " + id));
    }

    private Product updateExistingProduct(ProductUpdateRequest productUpdateRequest, Product existingProduct) {
        existingProduct.setName(productUpdateRequest.getName());
        existingProduct.setBrand(productUpdateRequest.getBrand());
        existingProduct.setPrice(productUpdateRequest.getPrice());
        existingProduct.setInventory(productUpdateRequest.getInventory());
        existingProduct.setDescription(productUpdateRequest.getDescription());
       Category category = categoryRepository.findByName(productUpdateRequest.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;

    }

    @Override
    public List<Product> getProducts(Long id) {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryName(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByPrice(Double price) {
        return List.of();
    }

    @Override
    public List<Product> getProductByCategoryNameAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
