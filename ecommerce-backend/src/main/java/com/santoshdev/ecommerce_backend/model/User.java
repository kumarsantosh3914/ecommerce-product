package com.santoshdev.ecommerce_backend.model;

import jakarta.persistence.*;

import java.util.Date;

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

    public enum Role {
        USER,
        ADMIN
    }
}
