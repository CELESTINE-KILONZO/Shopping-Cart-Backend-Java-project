package com.cellythebackenddeveloper.shopping_cart.dto;
import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private Long UserId;
    private String FirstName;
    private String LastName;
    private String Email;
    private List<OrderDto> orders;
    private CartDto cart;
}
