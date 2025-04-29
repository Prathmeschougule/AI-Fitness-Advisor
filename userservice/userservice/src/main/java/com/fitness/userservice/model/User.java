package com.fitness.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")          //create table in database
@Data                           //reduce the boiler pleat and provide the  getter setter
public class User {
   
    @Id
    @GeneratedValue(strategy =GenerationType.UUID )
    private String id;

    @Column(nullable = false)
    private String email;
    private String password;
    private String  firstName;
    private  String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role= UserRole.USER;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;


}
