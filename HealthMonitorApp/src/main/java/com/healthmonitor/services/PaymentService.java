package com.healthmonitor.services;

import com.healthmonitor.pojo.Payment;
import java.util.List;
import java.util.Map;

public interface PaymentService {

    List<Payment> getPayments(Map<String, String> params);

    Payment getPaymentById(int id);

    Payment createOrUpdatePayment(Payment payment);

    long countPayments(Map<String, String> params);

    String getRandomCode(int len);

    int createTransferPayment(Map<String, Object> bodyData, String username);

}
