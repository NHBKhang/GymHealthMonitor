package com.healthmonitor.controllers.api;

import com.healthmonitor.services.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin
public class ApiProgressController {
    
    @Autowired
    private ProgressService progressService;
    
}
