package com.healthmonitor.repositories;

import com.healthmonitor.pojo.Payment;
import java.util.List;
import java.util.Map;

public interface PaymentRepository {

    static final int PAGE_SIZE = 10;

    List<Payment> getPayments(Map<String, String> params);

    Payment getPaymentById(int id);

    Payment createOrUpdatePayment(Payment payment);

    long countPayments(Map<String, String> params);

    public static int getPageSize() {
        return PaymentRepository.PAGE_SIZE;
    }
    
}
