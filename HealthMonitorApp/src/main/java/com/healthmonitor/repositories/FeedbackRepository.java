package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Feedback;
import java.util.List;
import java.util.Map;

public interface FeedbackRepository {

    static final int PAGE_SIZE = 10;

    List<Feedback> getFeedbackList(Map<String, String> params);

    Feedback getFeedbackById(int id);

    Feedback createOrUpdateFeedback(Feedback feedback);

    void deleteFeedback(int id);
    
    void deleteFeedbackList(List<Integer> ids);

    long countFeedbackList(Map<String, String> params);
    
    String generateNextCode();

    public static int getPageSize() {
        return FeedbackRepository.PAGE_SIZE;
    }
    
}
