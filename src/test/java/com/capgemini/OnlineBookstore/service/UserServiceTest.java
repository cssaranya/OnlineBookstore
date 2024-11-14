package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.User;
import com.capgemini.OnlineBookstore.model.UserEntity;
import com.capgemini.OnlineBookstore.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@Transactional
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

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
        when(passwordEncoder.encode(anyString())).thenReturn(password);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        User user = User.builder()
                .userid(10L)
                .password(username)
                .username(password)
                .address("brussels")
                .email("css@test.com")
                .phonenumber("0987867")
                .build();
        User registeredUser = userService.registerUser(user);
        assertEquals(username,registeredUser.getUsername());
        assertEquals(password,registeredUser.getPassword());
    }

    @Test
    public void testLoadUserByUsername(){
        String username = "user";
        String password = "password";
        UserEntity user = new UserEntity();
        user.setPassword(password);
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
    }

    @Test
    public void testFindByUsername(){
        String username = "user";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        User savedUser = userService.findByUsername(username);
        assertEquals(username,savedUser.getUsername());
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        String username = "nouser";

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });
    }
}