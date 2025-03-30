package com.healthmonitor.services.impl;

import com.healthmonitor.pojo.Member;
import com.healthmonitor.pojo.Trainer;
import com.healthmonitor.pojo.User;
import com.healthmonitor.repositories.UserRepository;
import com.healthmonitor.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSeviceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers(Map<String, String> params) {
        return this.userRepository.getUsers(params);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepository.getUserById(id);
    }

    @Override
    public Member getMemberByUserId(int id) {
        return this.userRepository.getMemberByUserId(id);
    }

    @Override
    public Trainer getTrainerByUserId(int id) {
        return this.userRepository.getTrainerByUserId(id);
    }

    @Override
    public User createOrUpdateUser(User user) {
        return this.userRepository.createOrUpdateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        this.userRepository.deleteUser(id);
    }

    @Override
    public long countUsers(Map<String, String> params) {
        return this.userRepository.countUsers(params);
    }
}
