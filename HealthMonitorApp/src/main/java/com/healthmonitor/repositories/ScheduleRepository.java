package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Schedule;
import java.util.List;
import java.util.Map;

public interface ScheduleRepository {

    static final int PAGE_SIZE = 10;

    List<Schedule> getSchedules(Map<String, String> params);

    Schedule getScheduleById(int id);

    Schedule createOrUpdateSchedule(Schedule schedule);

    void deleteSchedule(int id);

    void deleteSchedules(List<Integer> ids);

    long countSchedules(Map<String, String> params);
    
    List<Schedule> getSchedulesByUsername(String username);

    String generateNextCode();

    public static int getPageSize() {
        return ScheduleRepository.PAGE_SIZE;
    }

}
