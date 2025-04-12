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
    private ProgressRepository progressRepository;
    
    @Override
    public List<Progress> getProgress(Map<String, String> params) {
        return this.progressRepository.getProgress(params);
    }
    
    @Override
    public Progress createOrUpdateProgress(Progress progress) {
        return this.progressRepository.createOrUpdateProgress(progress);
    }
    
    @Override
    public void deleteProgress(int id) {
        this.progressRepository.deleteProgress(id);
    }
    
    @Override
    public void deleteProgressList(List<Integer> ids) {
        this.progressRepository.deleteProgressList(ids);
    }
    
    @Override
    public long countProgress(Map<String, String> params) {
        return this.progressRepository.countProgress(params);
    }

    @Override
    public Progress getProgressById(int id) {
        return this.progressRepository.getProgressById(id);
    }

    @Override
    public String generateNextCode() {
        return progressRepository.generateNextCode();
    }
    
}
