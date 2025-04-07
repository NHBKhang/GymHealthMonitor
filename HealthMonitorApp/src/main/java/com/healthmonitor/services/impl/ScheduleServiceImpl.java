package com.healthmonitor.services.impl;

import com.healthmonitor.pojo.Schedule;
import com.healthmonitor.repositories.ScheduleRepository;
import com.healthmonitor.services.ScheduleService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> getSchedules(Map<String, String> params) {
        return this.scheduleRepository.getSchedules(params);
    }

    @Override
    public Schedule createOrUpdateSchedule(Schedule schedule) {
        if (schedule.getSubscription() == null || schedule.getSubscription().getId() == null) {
            schedule.setSubscription(null);
        }
        
        return this.scheduleRepository.createOrUpdateSchedule(schedule);
    }

    @Override
    public void deleteSchedule(int id) {
        this.scheduleRepository.deleteSchedule(id);
    }

    @Override
    public void deleteSchedules(List<Integer> ids) {
        this.scheduleRepository.deleteSchedules(ids);
    }

    @Override
    public long countSchedules(Map<String, String> params) {
        return this.scheduleRepository.countSchedules(params);
    }

    @Override
    public Schedule getScheduleById(int id) {
        return this.scheduleRepository.getScheduleById(id);
    }

    @Override
    public String generateNextCode() {
        return scheduleRepository.generateNextCode();
    }

}
