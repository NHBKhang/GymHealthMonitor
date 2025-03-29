package com.healthmonitor.services;

import com.healthmonitor.pojo.User;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getUsers(Map<String, String> params);
    User getUserById(int id);
    User createOrUpdateUser(User user);
    void deleteUser(int id);
}
