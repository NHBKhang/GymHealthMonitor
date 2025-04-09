package com.healthmonitor.services;

import java.time.LocalDate;
import java.util.Map;

public interface StatsService {

    Map<String, Object> getUserStats(LocalDate fromDate, LocalDate toDate);

}