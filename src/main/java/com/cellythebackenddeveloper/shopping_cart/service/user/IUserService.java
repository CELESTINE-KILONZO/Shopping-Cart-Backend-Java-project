package com.cellythebackenddeveloper.shopping_cart.service.user;

import com.cellythebackenddeveloper.shopping_cart.dto.UserDto;
import com.cellythebackenddeveloper.shopping_cart.model.User;
import com.cellythebackenddeveloper.shopping_cart.request.CreateUserRequest;
import com.cellythebackenddeveloper.shopping_cart.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(Long userId, UpdateUserRequest request);
    void deleteUser(Long id);

    UserDto convertToDto(User user);
}
