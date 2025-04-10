package com.healthmonitor.controllers.api;

import com.healthmonitor.components.JwtService;
import com.healthmonitor.pojo.Subscription;
import com.healthmonitor.serializers.SubscriptionSerializer;
import com.healthmonitor.services.SubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiSubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/my-subscriptions")
    public ResponseEntity<?> getMySubscriptions(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token xác thực");
            }

            String username = jwtService.getUsernameFromToken(token);
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
            }

            List<Subscription> subscriptions = subscriptionService.getSubscriptionsByUsername(username);
            return ResponseEntity.ok(SubscriptionSerializer.fromSubscriptions(subscriptions));
        } catch (Exception ex) {
            System.out.print(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lấy danh sách gói tập");
        }
    }
}
