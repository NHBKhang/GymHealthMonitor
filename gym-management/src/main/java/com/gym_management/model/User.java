package com.gym_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role",nullable = false)
    private String role; // admin, pt, member

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "phone",nullable = false)
    private String phone;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status", nullable = false)
    private String status; // active, inactive

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
