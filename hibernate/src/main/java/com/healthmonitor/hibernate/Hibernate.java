/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.healthmonitor.hibernate;

import com.healthmonitor.repositores.impl.UserRepositoryImpl;

/**
 *
 * @author KHANG
 */
public class Hibernate {

    public static void main(String[] args) {
        UserRepositoryImpl userRepo = new UserRepositoryImpl();
        
        boolean isCreated = userRepo.createUser(
            "admin", "admin123", "admin@example.com", 
            "Admin", "User", "ADMIN"
        );

        if (isCreated) {
            System.out.println("Admin user created.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
