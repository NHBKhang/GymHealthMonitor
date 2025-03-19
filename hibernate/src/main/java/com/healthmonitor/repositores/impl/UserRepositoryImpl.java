/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.healthmonitor.repositores.impl;

import com.healthmonitor.hibernate.HibernateUtils;
import com.healthmonitor.pojo.User;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author KHANG
 */
public class UserRepositoryImpl {
    public User getUserByUsername(String username) {
        try (Session s = HibernateUtils.getFACTORY().openSession()) {
            Query query = s.createNamedQuery("User.findByUsername", User.class);
            query.setParameter("username", username);
            
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean createUser(String username, String password, String email, String firstName, String lastName, String userRole) {
        Transaction tx = null;
        try (Session session = HibernateUtils.getFACTORY().openSession()) {
            if (getUserByUsername(username) != null) {
                System.out.println("User with username '" + username + "' already exists.");
                return false;
            }

            tx = session.beginTransaction();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUserRole(userRole);
            user.setActive(true);

            session.persist(user);
            tx.commit();

            System.out.println("User '" + username + "' has been created successfully.");
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }
}
