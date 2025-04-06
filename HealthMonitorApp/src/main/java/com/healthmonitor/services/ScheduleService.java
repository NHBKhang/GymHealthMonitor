package com.healthmonitor.services;

import com.healthmonitor.pojo.Schedule;
import java.util.List;
import java.util.Map;

public interface ScheduleService {

    List<Schedule> getSchedules(Map<String, String> params);

    Schedule getScheduleById(int id);

    Schedule createOrUpdateSchedule(Schedule schedule);

    void deleteSchedule(int id);

    void deleteSchedules(List<Integer> ids);

    long countSchedules(Map<String, String> params);

    String generateNextCode();

}
