package com.healthmonitor.serializers;

import com.healthmonitor.pojo.Payment;

public class PaymentSerializer extends Serializer<PaymentSerializer> {
    
    public PaymentSerializer(Payment payment) {
        this.id = payment.getId();
    }
}
