package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "subscriptions")
@NamedQueries({
        @NamedQuery(name = "Subscription.findAll", query = "SELECT s FROM Subscription s"),
        @NamedQuery(name = "Subscription.findById", query = "SELECT s FROM Subscription s WHERE s.id = :id"),
        @NamedQuery(name = "Subscription.findByMember", query = "SELECT s FROM Subscription s WHERE s.member.id = :memberId"),
        @NamedQuery(name = "Subscription.findByStatus", query = "SELECT s FROM Subscription s WHERE s.status = :status"),
        @NamedQuery(name = "Subscription.findActiveSubscriptions",
                query = "SELECT s FROM Subscription s WHERE s.status = 'active' AND s.endDate > CURRENT_DATE")
})
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User member;

    @JoinColumn(name = "package_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Package gymPackage ;

    @Column(name = "start_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Basic(optional = false)
    @Column(name = "status")
    private String status;

    @Basic(optional = false)
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subscription")
    private Set<Payment> paymentSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subscription")
    private Set<Schedule> scheduleSet;

    public Subscription() {
    }

    public Subscription(Integer id) {
        this.id = id;
    }

    public Subscription(Integer id, User member, Package gymPackage, Date startDate,
                        Date endDate, String status, Date createdAt) {
        this.id = id;
        this.member = member;
        this.gymPackage = gymPackage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public Package getGymPackage() {
        return gymPackage;
    }

    public void setGymPackage(Package gymPackage) {
        this.gymPackage = gymPackage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Payment> getPaymentSet() {
        return paymentSet;
    }

    public void setPaymentSet(Set<Payment> paymentSet) {
        this.paymentSet = paymentSet;
    }

    public Set<Schedule> getScheduleSet() {
        return scheduleSet;
    }

    public void setScheduleSet(Set<Schedule> scheduleSet) {
        this.scheduleSet = scheduleSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Subscription)) {
            return false;
        }
        Subscription other = (Subscription) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.healthmonitor.pojo.Subscription[ id=" + id + " ]";
    }
}