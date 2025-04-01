package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Member;
import com.healthmonitor.pojo.Trainer;
import com.healthmonitor.pojo.User;
import java.util.List;
import java.util.Map;

public interface UserRepository {

    List<User> getUsers(Map<String, String> params);

    User getUserById(int id);

    Member getMemberByUserId(int id);

    Trainer getTrainerByUserId(int id);

    User createOrUpdateUser(User user);

    void deleteUser(int id);

    long countUsers(Map<String, String> params);

    User getUserByUsername(String username);
    
    boolean authUser(String username, String password);
}
