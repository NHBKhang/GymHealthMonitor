package com.healthmonitor.serializers;

import com.healthmonitor.pojo.Schedule;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleSerializer extends Serializer<ScheduleSerializer> {
    
    public ScheduleSerializer(Schedule schedule) {
        this.id = schedule.getId();
        
    }

    public static List<ScheduleSerializer> fromSchedules(List<Schedule> schedules) {
        return schedules.stream()
                .map(ScheduleSerializer::new)
                .collect(Collectors.toList());
    }
}
