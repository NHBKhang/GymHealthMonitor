package com.healthmonitor.controllers;

import com.healthmonitor.services.StatsService;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping
    public String stats() {
        return "stats";
    }
    
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUserStats(
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        if (fromDate == null) {
            fromDate = LocalDate.now().minusMonths(1);
        }
        if (toDate == null) {
            toDate = LocalDate.now();
        }

        Map<String, Object> stats = statsService.getUserStats(fromDate, toDate);
        return ResponseEntity.ok(stats);
    }
}
