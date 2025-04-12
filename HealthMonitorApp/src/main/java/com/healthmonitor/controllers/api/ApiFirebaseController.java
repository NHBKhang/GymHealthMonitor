package com.healthmonitor.controllers.api;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.healthmonitor.components.FirebaseService;
import com.healthmonitor.components.JwtService;
import com.healthmonitor.pojo.Message;
import com.healthmonitor.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/messages")
@CrossOrigin
public class ApiFirebaseController {

    @Autowired
    private FirebaseService firebaseService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> getUserMessages(HttpServletRequest request) {
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

        int userId = userService.getUserByUsername(username).getId();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("messages");

        List<Message> messages = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot convSnap : snapshot.getChildren()) {
                    String convKey = convSnap.getKey();
                    if (convKey != null && convKey.contains(String.valueOf(userId))) {
                        for (DataSnapshot msgSnap : convSnap.getChildren()) {
                            Message msg = msgSnap.getValue(Message.class);
                            if (msg != null && (userId == msg.getSenderId() || userId == msg.getRecieverId())) {
                                messages.add(msg);
                            }
                        }
                    }
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Có lỗi xảy ra lấy tin nhắn!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

        return ResponseEntity.ok(messages);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> sendMessage(HttpServletRequest request,
            @RequestBody Message message) {
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

        firebaseService.sendMessage(
                userService.getUserByUsername(username).getId(), 
                message
        );
        return ResponseEntity.ok("Message sent");
    }
}
