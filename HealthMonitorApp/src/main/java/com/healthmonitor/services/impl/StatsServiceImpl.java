package com.healthmonitor.services.impl;

import com.healthmonitor.repositories.StatsRepository;
import com.healthmonitor.services.StatsService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Override
    public Map<String, Object> getUserStats(LocalDate fromDate, LocalDate toDate) {
        List<Object[]> results = this.statsRepository.getUserStats(fromDate, toDate);

        Map<String, Object> data = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Long> values = new ArrayList<>();

        for (Object[] row : results) {
            labels.add(row[0].toString());
            values.add((Long) row[1]);
        }

        data.put("labels", labels);
        data.put("values", values);
        return data;
    }

    @Override
    public Map<String, Object> getRevenueStats(LocalDate fromDate, LocalDate toDate) {
        List<Object[]> results = this.statsRepository.getRevenueStats(fromDate, toDate);

        Map<String, Object> data = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (Object[] row : results) {
            labels.add(row[0].toString());
            values.add((Double) row[1]);
        }

        data.put("labels", labels);
        data.put("values", values);
        return data;
    }
}