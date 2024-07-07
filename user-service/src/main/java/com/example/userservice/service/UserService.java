package com.example.userservice.service;

import com.example.userservice.domain.User;
import com.example.userservice.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<User> getUserByAll();
}

