package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.dto.User;
import com.capgemini.OnlineBookstore.exception.InvalidRequestException;
import com.capgemini.OnlineBookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user){
        validateRequest(user);
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("UserEntity registered successfully");
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user){
        validateRequest(user);
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    private static void validateRequest(User user) {
        String userName = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String address = user.getAddress();
        String phonenumber = user.getPhonenumber();
        if (userName == null || userName.isEmpty()){
            throw new InvalidRequestException("Registration failed : UserName is mandatory");
        }
        if (password == null || password.isEmpty()){
            throw new InvalidRequestException("Registration failed : Password is mandatory");
        }
        if (email == null || email.isEmpty()){
            throw new InvalidRequestException("Registration failed : Email is mandatory");
        }
        if (address == null || address.isEmpty()){
            throw new InvalidRequestException("Registration failed : Address is mandatory");
        }
        if (phonenumber == null || phonenumber.isEmpty()){
            throw new InvalidRequestException("Registration failed : Phonenumber is mandatory");
        }
    }
}
