package com.healthmonitor.controllers.api;

import com.healthmonitor.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin
public class ApiFeedbackController {
    
    @Autowired
    private FeedbackService feedbackService;
}
