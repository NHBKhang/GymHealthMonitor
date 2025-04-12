package com.healthmonitor.services;

import com.healthmonitor.pojo.Progress;
import java.util.List;
import java.util.Map;

public interface ProgressService {

    List<Progress> getProgress(Map<String, String> params);

    Progress getProgressById(int id);

    Progress createOrUpdateProgress(Progress progress);

    void deleteProgress(int id);
    
    void deleteProgressList(List<Integer> ids);

    long countProgress(Map<String, String> params);
    
    String generateNextCode();

}
