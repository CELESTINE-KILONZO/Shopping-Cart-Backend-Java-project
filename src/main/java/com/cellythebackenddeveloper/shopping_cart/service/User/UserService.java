package com.cellythebackenddeveloper.shopping_cart.service.User;
import com.cellythebackenddeveloper.shopping_cart.model.User;
import com.cellythebackenddeveloper.shopping_cart.repository.UserRepository;
import com.cellythebackenddeveloper.shopping_cart.request.CreateUserRequest;
import com.cellythebackenddeveloper.shopping_cart.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class UserService implements  IUserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return null;
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
//        userRepository.delete(user);
        userRepository.findById(id).ifPresentOrElse(userRepository ::delete, () -> {
            throw new RuntimeException("User not found with id: " + id); });

    }
}
