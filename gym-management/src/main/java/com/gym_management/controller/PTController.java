package com.gym_management.controller;

import com.gym_management.model.PT;
import com.gym_management.service.PTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pt")
public class PTController {
    @Autowired
    private PTService ptService;

    @GetMapping("/{id}")
    public ResponseEntity<PT> getPTById(@PathVariable Long id) {
        PT pt = ptService.getPTById(id);
        return ResponseEntity.ok(pt);
    }

    @PostMapping
    public ResponseEntity<PT> createPT(@RequestBody PT pt) {
        PT createdPT = ptService.createPT(pt);
        return ResponseEntity.ok(createdPT);
    }
}
