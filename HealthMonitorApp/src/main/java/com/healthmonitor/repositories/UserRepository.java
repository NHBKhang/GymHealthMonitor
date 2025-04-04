package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Member;
import com.healthmonitor.pojo.Trainer;
import com.healthmonitor.pojo.User;
import jakarta.data.repository.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserRepository {

    List<User> getUsers(Map<String, String> params);

    User getUserById(int id);

    Member getMemberByUserId(int id);

    Trainer getTrainerByUserId(int id);

    User createOrUpdateUser(User user);

    void deleteUser(int id);
    
    public void deleteUsers(List<Integer> ids);

    long countUsers(Map<String, String> params);

    User getUserByUsername(String username);
    
    boolean authUser(String username, String password);
    
    List<Object[]> getUserStats(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
