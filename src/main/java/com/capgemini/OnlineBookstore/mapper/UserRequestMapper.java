package com.capgemini.OnlineBookstore.mapper;

import com.capgemini.OnlineBookstore.dto.User;
import com.capgemini.OnlineBookstore.model.UserEntity;

public class UserRequestMapper {
    public UserEntity map(User user){
        return UserEntity.builder()
                .id(user.getId())
                .address(user.getAddress())
                .email(user.getEmail())
                .phonenumber(user.getPhonenumber())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
