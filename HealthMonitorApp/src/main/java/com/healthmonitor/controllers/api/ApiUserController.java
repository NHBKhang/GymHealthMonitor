package com.healthmonitor.controllers.api;

import com.healthmonitor.components.JwtService;
import com.healthmonitor.pojo.User;
import com.healthmonitor.serializers.UserSerializer;
import com.healthmonitor.services.UserService;
import com.healthmonitor.utils.Pagination;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody User user) {
        if (this.userService.authUser(user.getUsername(), user.getPassword()) == true) {
            String token = this.jwtService.generateTokenLogin(user.getUsername());

            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/current-user")
    public ResponseEntity<Object> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : null;

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ hoặc không tồn tại.");
        }

        String username = jwtService.getUsernameFromToken(token);

        if (username == null || jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token hết hạn hoặc không hợp lệ.");
        }

        User user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User không tồn tại.");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("user", new UserSerializer(user));

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<Map<String, Object>> list(@RequestParam Map<String, String> params) {
        try {
            return Pagination.handlePagination(
                    params,
                    userService::countUsers,
                    UserSerializer.fromUsers(userService.getUsers(params))
            );
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Có lỗi xảy ra khi tải danh sách người dùng!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping(path = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@ModelAttribute("user") User user) {
        return new ResponseEntity<>(
                new UserSerializer(this.userService.createOrUpdateUser(user)),
                HttpStatus.CREATED
        );
    }

    @PatchMapping(path = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@ModelAttribute("user") User user) {
        return new ResponseEntity<>(
                new UserSerializer(this.userService.createOrUpdateUser(user)),
                HttpStatus.OK
        );
    }
}
