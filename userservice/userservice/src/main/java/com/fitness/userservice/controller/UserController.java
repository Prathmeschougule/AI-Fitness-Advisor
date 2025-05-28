package com.fitness.userservice.controller;

import org.springframework.web.bind.annotation.*;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor    // we can also use the autowired and AllargsContructore
public class UserController {


    @Autowired
    UserService userService;
     
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String  userId) {
    return ResponseEntity.ok(userService.getUserProfile(userId));
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse>register(@Valid @RequestBody RegisterRequest request) {
    return  ResponseEntity.ok(userService.register(request));
    }


    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validuser(@PathVariable String  userId) {
        return ResponseEntity.ok(userService.existByUserID(userId));
    }
    
}
