package com.healthmonitor.utils;

import com.healthmonitor.serializers.Serializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.http.ResponseEntity;

public class Pagination {

    public static ResponseEntity<Map<String, Object>> handlePagination(Map<String, String> params,
            Function<Map<String, String>, Long> countMethod,
            List<?> records) {
        int page = params.containsKey("page") ? Integer.parseInt(params.get("page")) : 1;
        int pageSize = params.containsKey("size") ? Integer.parseInt(params.get("size")) : 10;

        long totalRecords = countMethod.apply(params);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        Map<String, Object> response = new HashMap<>();
        response.put("results", records);
        response.put("totalRecords", totalRecords);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return ResponseEntity.ok(response);
    }
}
