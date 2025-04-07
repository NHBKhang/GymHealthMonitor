package com.healthmonitor.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.healthmonitor.pojo.Member;
import com.healthmonitor.pojo.Trainer;
import com.healthmonitor.pojo.User;
import com.healthmonitor.pojo.User.Role;
import com.healthmonitor.pojo.User.UserStatus;
import com.healthmonitor.repositories.UserRepository;
import com.healthmonitor.services.UserService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
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
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        if (user.getFile() != null && !user.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Error uploading file to Cloudinary", ex);
            }
        }

        if (user.getRole() == null || user.getRoleName().isEmpty()) {
            user.setRole(Role.MEMBER);
        }

        if (user.getStatus() == null || user.getStatusName().isEmpty()) {
            user.setStatus(UserStatus.ACTIVE);
        }

        return this.userRepository.createOrUpdateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        this.userRepository.deleteUser(id);
    }

    @Override
    public void deleteUsers(List<Integer> ids) {
        this.userRepository.deleteUsers(ids);
    }

    @Override
    public long countUsers(Map<String, String> params) {
        return this.userRepository.countUsers(params);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        if (u.getStatus() == User.UserStatus.INACTIVE) {
            throw new UsernameNotFoundException("User is inactive!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + u.getRoleName()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public boolean authUser(String username, String password) {
        return this.userRepository.authUser(username, password);
    }

    @Override
    public Map<String, Object> getUserStats(LocalDate fromDate, LocalDate toDate) {
        List<Object[]> results = this.userRepository.getUserStats(fromDate, toDate);

        Map<String, Object> data = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Long> values = new ArrayList<>();

        for (Object[] row : results) {
            labels.add(row[0].toString());
            values.add((Long) row[1]);
        }

        data.put("labels", labels);
        data.put("values", values);
        return data;
    }
}
