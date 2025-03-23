package com.gym_management.service;

import com.gym_management.model.PT;
import com.gym_management.repository.PTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PTService {
    @Autowired
    private PTRepository ptRepository;

    public PT getPTById(Long id) {
        return ptRepository.findByPtId(id);
    }

    public PT createPT(PT pt) {
        return ptRepository.save(pt);
    }
}
