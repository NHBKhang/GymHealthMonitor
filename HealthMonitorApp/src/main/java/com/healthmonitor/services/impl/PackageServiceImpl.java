package com.healthmonitor.services.impl;

import com.healthmonitor.pojo.Package;
import com.healthmonitor.pojo.Package.PackageStatus;
import com.healthmonitor.repositories.PackageRepository;
import com.healthmonitor.services.PackageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageServiceImpl implements PackageService {
    
    @Autowired
    private PackageRepository packageRepository;
    
    @Override
    public List<Package> getPackages(Map<String, String> params) {
        return this.packageRepository.getPackages(params);
    }
    
    @Override
    public Package createOrUpdatePackage(Package pkg) {
        if (pkg.getStatus() == null || pkg.getStatusName().isEmpty()) {
            pkg.setStatus(PackageStatus.ACTIVE);
        }
        
        if (pkg.getCode() == null || pkg.getCode().isEmpty()) {
            pkg.setCode(this.generateNextCode());
        }
        
        return this.packageRepository.createOrUpdatePackage(pkg);
    }
    
    @Override
    public void deletePackage(int id) {
        this.packageRepository.deletePackage(id);
    }

    @Override
    public void deletePackages(List<Integer> ids) {
        this.packageRepository.deletePackages(ids);
    }
    
    @Override
    public long countPackages(Map<String, String> params) {
        return this.packageRepository.countPackages(params);
    }

    @Override
    public Package getPackageById(int id) {
        return this.packageRepository.getPackageById(id);
    }

    @Override
    public String generateNextCode() {
        return packageRepository.generateNextCode();
    }
    
}
