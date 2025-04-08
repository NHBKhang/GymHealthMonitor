package com.healthmonitor.services.impl;

import com.healthmonitor.pojo.Feedback;
import com.healthmonitor.repositories.FeedbackRepository;
import com.healthmonitor.services.FeedbackService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Override
    public List<Feedback> getFeedbackList(Map<String, String> params) {
        return this.feedbackRepository.getFeedbackList(params);
    }
    
    @Override
    public Feedback createOrUpdateFeedback(Feedback feedback) {
        if (feedback.getCode() == null || feedback.getCode().isEmpty()) {
            feedback.setCode(this.generateNextCode());
        }
        
        return this.feedbackRepository.createOrUpdateFeedback(feedback);
    }
    
    @Override
    public void deleteFeedback(int id) {
        this.feedbackRepository.deleteFeedback(id);
    }

    @Override
    public void deleteFeedbackList(List<Integer> ids) {
        this.feedbackRepository.deleteFeedbackList(ids);
    }
    
    @Override
    public long countFeedbackList(Map<String, String> params) {
        return this.feedbackRepository.countFeedbackList(params);
    }

    @Override
    public Feedback getFeedbackById(int id) {
        return this.feedbackRepository.getFeedbackById(id);
    }

    @Override
    public String generateNextCode() {
        return feedbackRepository.generateNextCode();
    }
    
}
