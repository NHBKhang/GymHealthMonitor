package com.healthmonitor.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.healthmonitor.pojo.Payment;
import com.healthmonitor.pojo.Payment.PaymentStatus;
import com.healthmonitor.pojo.Subscription;
import com.healthmonitor.repositories.PaymentRepository;
import com.healthmonitor.repositories.SubscriptionRepository;
import com.healthmonitor.services.PaymentService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Payment> getPayments(Map<String, String> params) {
        return this.paymentRepository.getPayments(params);
    }

    @Override
    public Payment createOrUpdatePayment(Payment payment) {
        if (payment.getStatus() == null || payment.getStatus().getLabel().isEmpty()) {
            payment.setStatus(PaymentStatus.PENDING);
        }

        return this.paymentRepository.createOrUpdatePayment(payment);
    }

    @Override
    public long countPayments(Map<String, String> params) {
        return this.paymentRepository.countPayments(params);
    }

    @Override
    public Payment getPaymentById(int id) {
        return this.paymentRepository.getPaymentById(id);
    }

    @Override
    public String getRandomCode(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return "P" + sb.toString();
    }

    @Override
    public Payment createTransferPayment(Map<String, Object> bodyData, String username) {
        try {
            Subscription sub = this.subscriptionRepository.createByPackageIdAndUsername(
                    Integer.parseInt(bodyData.get("package").toString()), username, true);

            Payment payment = new Payment();
            MultipartFile file = (MultipartFile) bodyData.get("file");
            if (file != null) {
                String contentType = file.getContentType();
                String resourceType = "auto";

                String publicId = "receipt_" + UUID.randomUUID();
                if ("application/pdf".equalsIgnoreCase(contentType)) {
                    publicId += ".pdf";
                    resourceType = "raw";
                }

                Map res = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap(
                                "resource_type", resourceType,
                                "public_id", publicId
                        ));
                payment.setReceiptImage(res.get("secure_url").toString());
            }

            payment.setSubscription(sub);
            payment.setAmount(Double.valueOf(bodyData.get("amount").toString()));
            payment.setStatus(PaymentStatus.PENDING);
            payment.setCode(this.getRandomCode(10));
            payment.setMethod(Payment.Method.TRANSFER);
            payment.setDescription("Đã gửi biên lai chuyển khoản vào ngày "
                    + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            return this.createOrUpdatePayment(payment);
        } catch (IOException | NumberFormatException e) {
            System.out.print(e);
            return null;
        }
    }

    @Override
    public Payment createVNPayPayment(Map<String, Object> bodyData, String username) {
        try {
            Subscription sub = this.subscriptionRepository.createByPackageIdAndUsername(
                    Integer.parseInt(bodyData.get("package").toString()), username, false);

            Payment payment = new Payment();

            payment.setSubscription(sub);
            payment.setAmount(Double.parseDouble(bodyData.get("amount").toString()) / 100);
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setCode(this.getRandomCode(10));
            payment.setMethod(Payment.Method.VNPAY);
            payment.setBankCode(bodyData.get("bankCode").toString());
            payment.setTransactionNo(bodyData.get("transactionNo").toString());
            payment.setDescription("Đã chuyển khoản vào ngày "
                    + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            return this.createOrUpdatePayment(payment);
        } catch (NumberFormatException e) {
            System.out.print(e);
            return null;
        }
    }

}
