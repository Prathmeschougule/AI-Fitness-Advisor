package com.fitness.gateway.user;

import lombok.Data;

import java.time.LocalDateTime;


@Data   //it is the provied the getters setters
public class UserResponse {

    private String id;
    private  String keycloakId;
    private String email;
    private String password;
    private String  firstName;
    private  String lastName;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
