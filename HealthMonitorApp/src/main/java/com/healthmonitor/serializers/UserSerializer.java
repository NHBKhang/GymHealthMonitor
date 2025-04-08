package com.healthmonitor.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.healthmonitor.pojo.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserSerializer extends Serializer<UserSerializer> {

    @JsonProperty("username")
    private final String username;

    @JsonProperty("full_name")
    private final String fullName;

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("phone")
    private final String phone;

    @JsonProperty("role")
    private final String role;

    @JsonProperty("avatar")
    private final String avatar;

    public UserSerializer(User user) {
        this.id = user.getId();
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRoleName();
        this.avatar = user.getAvatar() == null
                ? "http://localhost:8080/HealthMonitorApp/images/default_avatar.svg"
                : user.getAvatar();
    }

    public static List<UserSerializer> fromUsers(List<User> users) {
        return users.stream()
                .map(UserSerializer::new)
                .collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return lastName;
    }

    public String getLastName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getAvatar() {
        return avatar;
    }
}
