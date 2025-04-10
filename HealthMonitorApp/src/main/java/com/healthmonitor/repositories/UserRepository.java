package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Member;
import com.healthmonitor.pojo.Trainer;
import com.healthmonitor.pojo.User;
import java.util.List;
import java.util.Map;

public interface UserRepository {

    static final int PAGE_SIZE = 10;

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

    public static int getPageSize() {
        return UserRepository.PAGE_SIZE;
    }

}
