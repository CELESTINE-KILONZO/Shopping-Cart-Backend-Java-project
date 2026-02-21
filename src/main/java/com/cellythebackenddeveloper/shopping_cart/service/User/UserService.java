package com.cellythebackenddeveloper.shopping_cart.service.User;
import com.cellythebackenddeveloper.shopping_cart.dto.UserDto;
import com.cellythebackenddeveloper.shopping_cart.exceptions.AlreadyExistException;
import com.cellythebackenddeveloper.shopping_cart.model.User;
import com.cellythebackenddeveloper.shopping_cart.repository.UserRepository;
import com.cellythebackenddeveloper.shopping_cart.request.CreateUserRequest;
import com.cellythebackenddeveloper.shopping_cart.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service

public class UserService implements  IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).map(req -> {
            User user = new User();
            user.setFirstName(req.getFirstName());
            user.setLastName(req.getLastName());
            user.setEmail(req.getEmail());
            user.setPassword(req.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new AlreadyExistException("User with email " + request.getEmail() + " already exists"));
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequest request) {
        return  userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository ::delete, () -> {
            throw new RuntimeException("User not found with id: " + id); });

    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
