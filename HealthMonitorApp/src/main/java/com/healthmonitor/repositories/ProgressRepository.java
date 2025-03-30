package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Progress;
import java.util.List;
import java.util.Map;

public interface ProgressRepository {
    List<Progress> getProgress(Map<String, String> params);

    Progress getProgressById(int id);

    Progress createOrUpdateProgress(Progress progress);

    void deleteProgress(int id);

    long countProgress(Map<String, String> params);
}
