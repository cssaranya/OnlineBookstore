package com.capgemini.OnlineBookstore.mapper;

import com.capgemini.OnlineBookstore.dto.User;
import com.capgemini.OnlineBookstore.model.UserEntity;

public class UserResponseMapper {
    public User map(UserEntity userEntity){
        return User.builder()
                .id(userEntity.getId())
                .address(userEntity.getAddress())
                .email(userEntity.getEmail())
                .phonenumber(userEntity.getPhonenumber())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }
}
