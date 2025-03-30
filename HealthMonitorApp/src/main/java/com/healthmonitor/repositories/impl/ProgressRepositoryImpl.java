package com.healthmonitor.repositories.impl;

import com.healthmonitor.pojo.Progress;
import com.healthmonitor.repositories.ProgressRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProgressRepositoryImpl implements ProgressRepository {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Progress> getProgress(Map<String, String> params) {
        return null;
    }

    @Override
    public Progress createOrUpdateProgress(Progress progress) {
        return null;
    }

    @Override
    public void deleteProgress(int id) {

    }

    @Override
    public long countProgress(Map<String, String> params) {
        return 1;
    }

    public static final int getPageSize() {
        return ProgressRepositoryImpl.PAGE_SIZE;
    }

    @Override
    public Progress getProgressById(int id) {
        return null;
    }
}
