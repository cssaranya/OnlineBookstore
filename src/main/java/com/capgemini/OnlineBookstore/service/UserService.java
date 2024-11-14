package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.User;
import com.capgemini.OnlineBookstore.mapper.UserRequestMapper;
import com.capgemini.OnlineBookstore.mapper.UserResponseMapper;
import com.capgemini.OnlineBookstore.model.UserEntity;
import com.capgemini.OnlineBookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserRequestMapper userRequestMapper = new UserRequestMapper();
    UserResponseMapper userResponseMapper = new UserResponseMapper();

    public User registerUser(User user){
        String encodedPwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPwd);
        UserEntity userEntity = userRequestMapper.map(user);
        return userResponseMapper.map(userRepository.save(userEntity));
    }

    public User getUserById(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("UserEntity not found with id "+ id));
        return userResponseMapper.map(userEntity);
    }

    public User updateUser(Long id, User user){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("UserEntity not found with id "+ id));

        userEntity.setPhonenumber(user.getPhonenumber());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        return userResponseMapper.map(userRepository.save(userEntity));
    }

    public User findByUsername(String userName){
        UserEntity userEntity = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("UserEntity not found with id name"+ userName));
        return userResponseMapper.map(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
