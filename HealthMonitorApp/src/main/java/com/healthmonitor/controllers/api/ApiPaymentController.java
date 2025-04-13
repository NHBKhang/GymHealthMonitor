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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> vnpayReturn(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, Object> res = new HashMap<>();

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu token xác thực");
        }

        String username = jwtService.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
        }

        Map<String, Object> bodyData = new HashMap<>();

        String vnp_Amount = request.getParameter("vnp_Amount");
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_BankCode = request.getParameter("vnp_BankCode");
        String[] parts = vnp_OrderInfo.split("#");
        if (parts.length == 2) {
            bodyData.put("package", parts[1]);
        }

        bodyData.put("amount", vnp_Amount);
        bodyData.put("transactionNo", vnp_TransactionNo);
        bodyData.put("bankCode", vnp_BankCode);

        if (this.paymentService.createVNPayPayment(bodyData, username) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tạo đơn hàng thất bại");
        }

        int result = vnpayService.paymentReturn(request);
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
    public ResponseEntity<?> doTransferPayment(
            @RequestParam("package") int packageId,
            @RequestParam("amount") double amount,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
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

            Map<String, Object> bodyData = new HashMap<>();
            bodyData.put("package", packageId);
            bodyData.put("amount", amount);
            bodyData.put("file", file);

            Payment payment = this.paymentService.createTransferPayment(bodyData, username);

            if (payment != null) {
                res.put("code", 1);
                res.put("message", "Thanh toán thành công");
                res.put("paymentCode", payment.getCode());
            } else {
                res.put("code", 0);
                res.put("message", "Thanh toán thất bại");
            }

            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("code", -1);
            res.put("message", "Lỗi hệ thống");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }
}
