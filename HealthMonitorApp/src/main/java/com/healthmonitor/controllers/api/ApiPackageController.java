package com.healthmonitor.controllers.api;

import com.healthmonitor.pojo.Package;
import com.healthmonitor.services.PackageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/packages")
public class ApiPackageController {
    
    @Autowired
    private PackageService packageService;
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Package> create(@ModelAttribute("package") Package pkg) {
        return new ResponseEntity<>(this.packageService.createOrUpdatePackage(pkg), HttpStatus.CREATED);
    }
    
    @GetMapping
    @CrossOrigin
    public ResponseEntity<List<Package>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.packageService.getPackages(params), HttpStatus.OK);
    }
}
