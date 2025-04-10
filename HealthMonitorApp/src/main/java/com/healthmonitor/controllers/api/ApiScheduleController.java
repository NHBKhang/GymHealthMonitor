package com.healthmonitor.controllers.api;

import com.healthmonitor.components.JwtService;
import com.healthmonitor.pojo.Schedule;
import com.healthmonitor.serializers.ScheduleSerializer;
import com.healthmonitor.services.ScheduleService;
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
public class ApiScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/my-schedules")
    public ResponseEntity<?> getMySchedules(HttpServletRequest request) {
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

            List<Schedule> schedules = scheduleService.getSchedulesByUsername(username);
            return ResponseEntity.ok(ScheduleSerializer.fromSchedules(schedules));
        } catch (Exception ex) {
            System.out.print(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lấy danh sách gói tập");
        }
    }
}
