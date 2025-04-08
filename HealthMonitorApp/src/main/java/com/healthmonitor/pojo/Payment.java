package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "payments")
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE p.id = :id"),
    @NamedQuery(name = "Payment.findBySubscription",
            query = "SELECT p FROM Payment p WHERE p.subscription.id = :subscriptionId"),
    @NamedQuery(name = "Payment.findByStatus", query = "SELECT p FROM Payment p WHERE p.status = :status"),
    @NamedQuery(name = "Payment.findByMethod", query = "SELECT p FROM Payment p WHERE p.method = :method")
})
public class Payment implements Serializable {

    public enum PaymentStatus {
        PENDING("Đang xử lý", "badge-warning"),
        SUCCESS("Thành công", "badge-success"),
        FAILED("Thất bại", "badge-danger"),
        CANCELLED("Đã huỷ", "badge-secondary"),
        REFUNDED("Đã hoàn tiền", "badge-info");

        private final String label;
        private final String badgeClass;

        private PaymentStatus(String label, String badgeClass) {
            this.label = label;
            this.badgeClass = badgeClass;
        }

        public String getLabel() {
            return label;
        }

        public String getBadgeClass() {
            return badgeClass;
        }
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Subscription subscription;

    @Basic(optional = false)
    @Column(name = "amount")
    private Double amount;

    @Basic(optional = false)
    @Column(name = "method")
    private String method;

    @Column(name = "receipt_image", nullable = true)
    private String receiptImage;

    @Basic(optional = false)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    public Payment() {
    }

    public Payment(Integer id) {
        this.id = id;
    }

    public Payment(Integer id, String code, Subscription subscription, Double amount,
            String method, PaymentStatus status, Date createdAt, String description) {
        this.id = id;
        this.code = code;
        this.subscription = subscription;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.createdAt = createdAt;
        this.description = description;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReceiptImage() {
        return receiptImage;
    }

    public void setReceiptImage(String receiptImage) {
        this.receiptImage = receiptImage;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.healthmonitor.pojo.Payment[ code=" + code + " ]";
    }
}
