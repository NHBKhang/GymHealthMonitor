package com.healthmonitor.components;

import com.healthmonitor.pojo.User;
import com.healthmonitor.pojo.User.Role;
import com.healthmonitor.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@DependsOn("userServiceImpl")
public class DataInitializer {

    @Autowired
    @Lazy
    private UserService userService;

    @PostConstruct
    public void init() {
        if (userService.getUserByUsername("admin") == null) {
            User admin = new User("admin", "admin", "admin@example.com", Role.ADMIN, "Nguyễn", "Admin", "0123456789");
            User trainer = new User("trainer", "trainer", "trainer@example.com", Role.TRAINER, "Trần Thị", "Na", "0967456615");
            User member = new User("member", "member", "member@example.com", Role.MEMBER, "Lê Văn", "An", "0765661227");

            userService.createOrUpdateUser(admin);
            userService.createOrUpdateUser(trainer);
            userService.createOrUpdateUser(member);
        }
    }
}
