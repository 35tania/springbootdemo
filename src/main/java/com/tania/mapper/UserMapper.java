package com.tania.mapper;

import com.tania.dto.OrderDto;
import com.tania.dto.OrderItemDto;
import com.tania.dto.UserDto;
import com.tania.model.Order;
import com.tania.model.OrderItem;
import com.tania.model.User;

public class UserMapper {

    public static UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
