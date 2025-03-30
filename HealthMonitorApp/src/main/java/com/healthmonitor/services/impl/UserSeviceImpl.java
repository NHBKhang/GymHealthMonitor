package com.healthmonitor.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.healthmonitor.pojo.Member;
import com.healthmonitor.pojo.Trainer;
import com.healthmonitor.pojo.User;
import com.healthmonitor.repositories.UserRepository;
import com.healthmonitor.services.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSeviceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;

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
        if (user.getFile() != null && !user.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserSeviceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Error uploading file to Cloudinary", ex);
            }
        }
        
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
