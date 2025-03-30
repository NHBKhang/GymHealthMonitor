package com.healthmonitor.services.impl;

import com.healthmonitor.pojo.Progress;
import com.healthmonitor.repositories.ProgressRepository;
import com.healthmonitor.services.ProgressService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressServiceImpl implements ProgressService {
    
    @Autowired
    private ProgressRepository userRepository;
    
    @Override
    public List<Progress> getProgress(Map<String, String> params) {
        return this.userRepository.getProgress(params);
    }
    
    @Override
    public Progress createOrUpdateProgress(Progress progress) {
        return this.userRepository.createOrUpdateProgress(progress);
    }
    
    @Override
    public void deleteProgress(int id) {
        this.userRepository.deleteProgress(id);
    }
    
    @Override
    public long countProgress(Map<String, String> params) {
        return this.userRepository.countProgress(params);
    }

    @Override
    public Progress getProgressById(int id) {
        return this.userRepository.getProgressById(id);
    }
    
}
