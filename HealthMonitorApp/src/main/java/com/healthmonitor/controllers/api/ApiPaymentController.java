package com.healthmonitor.controllers.api;

import com.healthmonitor.components.JwtService;
import com.healthmonitor.components.VNPayService;
import com.healthmonitor.pojo.Payment;
import com.healthmonitor.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private JwtService jwtService;

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

    @PostMapping(path = "/transfer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> doTransferPayment(@RequestBody Map<String, Object> bodyData,
            HttpServletRequest request) {
        try {
            String file = (String) bodyData.get("file");
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("Biên lai không được để trống!");
            }

            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token xác thực");
            }

            String username = jwtService.getUsernameFromToken(token);
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
            }

            int result = this.paymentService.createTransferPayment(bodyData, username);

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
                    res.put("message", "Lỗi hệ thống");
                    break;
            }

            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xử lý thanh toán: " + e.getMessage());
        }
    }
}
