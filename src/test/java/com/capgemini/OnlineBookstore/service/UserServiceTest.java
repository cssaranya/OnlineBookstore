package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.User;
import com.capgemini.OnlineBookstore.model.UserEntity;
import com.capgemini.OnlineBookstore.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@DirtiesContext
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    UserService userService;

    @Test
    public void testRegisterUser(){
        String username = "css";
        String password = "css";
        UserEntity userEntity = UserEntity.builder()
                .id(10L)
                .password(username)
                .username(password)
                .address("brussels")
                .email("css@test.com")
                .phonenumber("0987867")
                .build();

        User userDTO = User.builder()
                .id(10L)
                .password(username)
                .username(password)
                .address("brussels")
                .email("css@test.com")
                .phonenumber("0987867")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn(password);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        when(modelMapper.map(userDTO, UserEntity.class)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, User.class)).thenReturn(userDTO);

        User registeredUser = userService.registerUser(userDTO);
        assertEquals(username,registeredUser.getUsername());
        assertEquals(password,registeredUser.getPassword());
    }

    @Test
    public void testLoadUserByUsername(){
        String username = "user";
        String password = "password";
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(password);
        userEntity.setUsername(username);

        User userDTO = new User();
        userDTO.setPassword(password);
        userDTO.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userDTO, UserEntity.class)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, User.class)).thenReturn(userDTO);
        UserDetails userDetails = userService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
    }

    @Test
    public void testFindByUsername(){
        String username = "user";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        User userDTO = new User();
        userDTO.setUsername(username);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntity));

        when(modelMapper.map(userDTO, UserEntity.class)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, User.class)).thenReturn(userDTO);
        User savedUser = userService.findByUsername(username);
        assertEquals(username,savedUser.getUsername());
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        String username = "nouser";

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }
}