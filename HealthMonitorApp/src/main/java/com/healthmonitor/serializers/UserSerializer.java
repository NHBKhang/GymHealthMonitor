package com.healthmonitor.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.healthmonitor.pojo.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserSerializer {

    @JsonProperty("id")
    private final int id;

    @JsonProperty("full_name")
    private final String fullName;

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("role")
    private final String role;

    public UserSerializer(User user) {
        this.id = user.getId();
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRoleName();
    }

    public static List<UserSerializer> fromUsers(List<User> users) {
        return users.stream()
                .map(UserSerializer::new)
                .collect(Collectors.toList());
    }

    public int getId() {
        return id;
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

    public String getRole() {
        return role;
    }
}
