package com.healthmonitor.controllers.api;

import com.healthmonitor.pojo.Package;
import com.healthmonitor.serializers.PackageSerializer;
import com.healthmonitor.services.PackageService;
import com.healthmonitor.utils.Pagination;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin
public class ApiPackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PackageSerializer> create(@ModelAttribute("package") Package pkg) {
        return new ResponseEntity<>(
                new PackageSerializer(this.packageService.createOrUpdatePackage(pkg)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(@RequestParam Map<String, String> params) {
        try {
            return Pagination.handlePagination(
                    params,
                    packageService::countPackages,
                    PackageSerializer.fromPackages(packageService.getPackages(params))
            );
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Có lỗi xảy ra khi tải danh sách người dùng!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> retrieve(@PathVariable("id") int id) {
        try {
            Package pkg = packageService.getPackageById(id);

            if (pkg == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Không tìm thấy gói tập với ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            return ResponseEntity.ok(new PackageSerializer(pkg));
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Đã xảy ra lỗi khi lấy gói tập!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
