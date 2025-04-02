package com.healthmonitor.services;

import com.healthmonitor.pojo.Package;
import java.util.List;
import java.util.Map;

public interface PackageService {

    List<Package> getPackage(Map<String, String> params);

    Package getPackageById(int id);

    Package createOrUpdateProgress(Package progress);

    void deletePackage(int id);

    long countPackage(Map<String, String> params);
    
}
