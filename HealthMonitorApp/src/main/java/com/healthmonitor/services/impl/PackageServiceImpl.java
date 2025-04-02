package com.healthmonitor.services.impl;

import com.healthmonitor.pojo.Package;
import com.healthmonitor.services.PackageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageServiceImpl implements PackageService {
    
    @Autowired
    private PackageService packageRepository;
    
    @Override
    public List<Package> getPackage(Map<String, String> params) {
        return this.packageRepository.getPackage(params);
    }
    
    @Override
    public Package createOrUpdateProgress(Package progress) {
        return this.packageRepository.createOrUpdateProgress(progress);
    }
    
    @Override
    public void deletePackage(int id) {
        this.packageRepository.deletePackage(id);
    }
    
    @Override
    public long countPackage(Map<String, String> params) {
        return this.packageRepository.countPackage(params);
    }

    @Override
    public Package getPackageById(int id) {
        return this.packageRepository.getPackageById(id);
    }
    
}
