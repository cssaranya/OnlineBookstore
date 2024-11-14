package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.dto.User;
import com.capgemini.OnlineBookstore.exception.InvalidRequestException;
import com.capgemini.OnlineBookstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody User user){
        validateRequest(user);
        userService.registerUser(user);
        return "UserEntity registered successfully";
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
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
        if (userName == null || "".equals(userName)){
            throw new InvalidRequestException("Registration failed : UserName is mandatory");
        }
        if (password == null || "".equals(password)){
            throw new InvalidRequestException("Registration failed : Password is mandatory");
        }
        if (email == null || "".equals(email)){
            throw new InvalidRequestException("Registration failed : Email is mandatory");
        }
        if (address == null || "".equals(address)){
            throw new InvalidRequestException("Registration failed : Address is mandatory");
        }
        if (phonenumber == null || "".equals(phonenumber)){
            throw new InvalidRequestException("Registration failed : Phonenumber is mandatory");
        }
    }
}
