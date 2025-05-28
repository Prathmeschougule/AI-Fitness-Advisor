package com.fitness.gateway.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    
    //thats we needed from the user

    @NotBlank(message = "Email is Required ")
    @Email(message = "Invalid email format")
    private String email;
    private  String keycloakId;
    
    @NotBlank (message = "Password is required")
    @Size(min = 6 ,message="Password must be at list 6 character")
    private String password;


    private String firstName;


    private String  lastName;


}
