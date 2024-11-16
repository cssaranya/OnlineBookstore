package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.User;
import com.capgemini.OnlineBookstore.exception.UserNotFoundException;
import com.capgemini.OnlineBookstore.model.UserEntity;
import com.capgemini.OnlineBookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public User registerUser(User user){
        String encodedPwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPwd);
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        return modelMapper.map(userRepository.save(userEntity), User.class);
    }

    public User getUserById(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("UserEntity not found with id "+ id));
        return modelMapper.map(userEntity, User.class);
    }

    public User updateUser(Long id, User user){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("UserEntity not found with id "+ id));

        userEntity.setPhonenumber(user.getPhonenumber());
        userEntity.setEmail(user.getEmail());
        userEntity.setAddress(user.getAddress());
        userEntity.setUsername(user.getPassword());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        return modelMapper.map(userRepository.save(userEntity), User.class);
    }

    public User findByUsername(String userName){
        UserEntity userEntity = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException("UserEntity not found with id name"+ userName));
        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
