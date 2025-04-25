package com.fitness.userservice.dto;

import java.time.LocalDateTime;

import lombok.Data;


@Data   //it is the provied the getters setters
public class UserResponse{

    private String id;
    private String email;
    private String password;
    private String  firstName;
    private  String lastName;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
