package com.healthmonitor.controllers.api;

import com.healthmonitor.components.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class ApiPaymentController {

    @Autowired
    private VNPayService vnpayService;

    @PostMapping("/vnpay")
    public Map<String, String> doVNPayPayment(@RequestBody Map<String, Object> bodyData) {
        String vnpayUrl = vnpayService.createPayment(bodyData);

        Map<String, String> response = new HashMap<>();
        response.put("payUrl", vnpayUrl);
        return response;
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<Map<String, Object>> vnpayReturn(HttpServletRequest request) {
        int result = vnpayService.paymentReturn(request);

        Map<String, Object> res = new HashMap<>();
        switch (result) {
            case 1:
                res.put("code", 1);
                res.put("message", "Thanh toán thành công");
                break;
            case 0:
                res.put("code", 0);
                res.put("message", "Thanh toán thất bại");
                break;
            case -1:
            default:
                res.put("code", -1);
                res.put("message", "Chữ ký không hợp lệ");
                break;
        }

        return ResponseEntity.ok(res);
    }
}
