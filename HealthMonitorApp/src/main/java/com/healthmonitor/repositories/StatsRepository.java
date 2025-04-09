package com.healthmonitor.repositories;

import jakarta.data.repository.Param;
import java.time.LocalDate;
import java.util.List;

public interface StatsRepository {

    List<Object[]> getUserStats(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

}
