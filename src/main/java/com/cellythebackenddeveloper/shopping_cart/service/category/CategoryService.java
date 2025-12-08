package com.cellythebackenddeveloper.shopping_cart.service.category;
import com.cellythebackenddeveloper.shopping_cart.exceptions.AlreadyExistException;
import com.cellythebackenddeveloper.shopping_cart.exceptions.ResourceNotException;
import com.cellythebackenddeveloper.shopping_cart.model.Category;
import com.cellythebackenddeveloper.shopping_cart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistException("Category already exists with name: " + category.getName()));
    }


    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotException("Category not found with id: " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, String name) {
        return categoryRepository.findById(id)
                .map(oldCategory -> {
                    oldCategory.setName(name);
                    return categoryRepository.save(oldCategory);
                })
                .orElseThrow(() -> new ResourceNotException("Category not found with id: " + id));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(
                        categoryRepository::delete,
                        () -> {
                            throw new ResourceNotException("Category not found with id: " + id);
                        }
                );
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
