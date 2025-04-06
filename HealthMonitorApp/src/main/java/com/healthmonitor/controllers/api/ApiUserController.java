package com.healthmonitor.controllers.api;

import com.healthmonitor.components.JwtService;
import com.healthmonitor.pojo.User;
import com.healthmonitor.repositories.impl.UserRepositoryImpl;
import com.healthmonitor.serializers.UserSerializer;
import com.healthmonitor.services.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody User user) {
        if (this.userService.authUser(user.getUsername(), user.getPassword()) == true) {
            String token = this.jwtService.generateTokenLogin(user.getUsername());

            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<User> create(@ModelAttribute("user") User user) {
        return new ResponseEntity<>(this.userService.createOrUpdateUser(user), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    @CrossOrigin
    public ResponseEntity<Map<String, Object>> list(@RequestParam Map<String, String> params) {
        try {
            int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
            int pageSize = UserRepositoryImpl.getPageSize();

            long totalUsers = userService.countUsers(params);
            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

            List<User> users = userService.getUsers(params);

            Map<String, Object> response = new HashMap<>();
            response.put("results", UserSerializer.fromUsers(users));
            response.put("totalUsers", totalUsers);
            response.put("totalPages", totalPages);
            response.put("currentPage", page);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Có lỗi xảy ra khi tải danh sách người dùng!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
