package com.healthmonitor.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.healthmonitor.pojo.Package;
import com.healthmonitor.pojo.Package.PackageStatus;
import com.healthmonitor.repositories.PackageRepository;
import com.healthmonitor.services.PackageService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageServiceImpl implements PackageService {
    
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private Cloudinary cloudinary;
    
    @Override
    public List<Package> getPackages(Map<String, String> params) {
        return this.packageRepository.getPackages(params);
    }
    
    @Override
    public Package createOrUpdatePackage(Package pkg) {
        if (pkg.getFile() != null && !pkg.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(pkg.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                pkg.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Error uploading file to Cloudinary", ex);
            }
        }

        if (pkg.getStatus() == null || pkg.getStatusName().isEmpty()) {
            pkg.setStatus(PackageStatus.ACTIVE);
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
