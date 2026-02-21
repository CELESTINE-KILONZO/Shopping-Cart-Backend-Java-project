package com.cellythebackenddeveloper.shopping_cart.controller;
import com.cellythebackenddeveloper.shopping_cart.exceptions.AlreadyExistException;
import com.cellythebackenddeveloper.shopping_cart.exceptions.ResourceNotException;
import com.cellythebackenddeveloper.shopping_cart.model.User;
import com.cellythebackenddeveloper.shopping_cart.request.CreateUserRequest;
import com.cellythebackenddeveloper.shopping_cart.request.UpdateUserRequest;
import com.cellythebackenddeveloper.shopping_cart.response.ApiResponse;
import com.cellythebackenddeveloper.shopping_cart.service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")

public class UserController {
    private final IUserService userService;
    @GetMapping("/{userId}/getUserById")
    public ResponseEntity <ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse("User retrieved successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PostMapping("/createUser")
    public ResponseEntity <ApiResponse> createNewUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            return ResponseEntity.ok(new ApiResponse("User created successfully", user));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PutMapping("/{userId}/updateUser")
    public ResponseEntity <ApiResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        try {
            User updatedUser = userService.updateUser(userId, request);
            return ResponseEntity.ok(new ApiResponse("User updated successfully", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
}
    }
    @DeleteMapping("/{userId}/deleteUser")
    public ResponseEntity <ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully", null));
        } catch (ResourceNotException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
