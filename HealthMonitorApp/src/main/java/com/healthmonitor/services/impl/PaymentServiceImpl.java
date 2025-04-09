package com.healthmonitor.services.impl;

import com.healthmonitor.pojo.Payment;
import com.healthmonitor.pojo.Payment.PaymentStatus;
import com.healthmonitor.repositories.PaymentRepository;
import com.healthmonitor.services.PaymentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

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

}
