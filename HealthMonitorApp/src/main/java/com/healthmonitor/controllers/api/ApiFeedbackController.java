package com.healthmonitor.controllers.api;

import com.healthmonitor.pojo.Feedback;
import com.healthmonitor.serializers.FeedbackSerializer;
import com.healthmonitor.services.FeedbackService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin
public class ApiFeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@ModelAttribute("feedback") Feedback feedback) {
        try {
            return new ResponseEntity<>(
                    new FeedbackSerializer(this.feedbackService.createOrUpdateFeedback(feedback)),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Có lỗi xảy ra gửi đánh giá!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
