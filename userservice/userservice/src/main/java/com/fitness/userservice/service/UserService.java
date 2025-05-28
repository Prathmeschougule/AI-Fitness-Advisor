package com.fitness.userservice.service;

import com.fitness.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;



@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            User existingUser =userRepository.findByEmail(request.getEmail());

            UserResponse response=new UserResponse();

            response.setId(existingUser.getId());
            response.setKeycloakId(existingUser.getKeycloakId());
            response.setEmail(existingUser.getEmail());
            response.setPassword(existingUser.getPassword());
            response.setFirstName(existingUser.getFirstName());
            response.setLastName(existingUser.getLastName());
            response.setCreateAt(existingUser.getCreateAt());
            response.setUpdateAt(existingUser.getUpdateAt());

            return response;
        }

        User user = new  User();

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setKeycloakId(request.getKeycloakId());
        user.setFirstName( request.getFirstName());
        user.setLastName(request.getLastName());

//       save the user in database
        User savedUser =userRepository.save(user);

        UserResponse response=new UserResponse();

        response.setId(savedUser.getId());
        response.setKeycloakId(savedUser.getKeycloakId());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCreateAt(savedUser.getCreateAt());
        response.setUpdateAt(savedUser.getUpdateAt());

        return  response;



    }

    public UserResponse getUserProfile(String userId) {
        User user= userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Id Not Found"));

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCreateAt(user.getCreateAt());
        response.setUpdateAt(user.getUpdateAt());

        return  response;


    }

    public Boolean existByUserID(String userId) {
        log.info(" Calling Validation API for  the UserId: {} ", userId);
        return userRepository.existsById(userId);

    }

    public Boolean existByKeycloakId(String userId) {
        log.info(" Calling Validation API for  the UserId: {} ", userId);
        return userRepository.existsById(userId);
    }
}
