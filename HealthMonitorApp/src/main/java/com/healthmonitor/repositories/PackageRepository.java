package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Package;
import java.util.List;
import java.util.Map;

public interface PackageRepository {

    static final int PAGE_SIZE = 10;

    List<Package> getPackages(Map<String, String> params);

    Package getPackageById(int id);

    Package createOrUpdatePackage(Package pkg);

    void deletePackage(int id);
    
    void deletePackages(List<Integer> ids);

    long countPackages(Map<String, String> params);
    
    String generateNextCode();

    public static int getPageSize() {
        return PackageRepository.PAGE_SIZE;
    }
    
}
