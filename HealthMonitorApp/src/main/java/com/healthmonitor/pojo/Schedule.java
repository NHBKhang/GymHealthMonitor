package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "schedules")
@NamedQueries({
        @NamedQuery(name = "Schedule.findAll", query = "SELECT s FROM Schedule s"),
        @NamedQuery(name = "Schedule.findById", query = "SELECT s FROM Schedule s WHERE s.id = :id"),
        @NamedQuery(name = "Schedule.findByMember",
                query = "SELECT s FROM Schedule s WHERE s.member.id = :memberId"),
        @NamedQuery(name = "Schedule.findByPT",
                query = "SELECT s FROM Schedule s WHERE s.pt.id = :ptId"),
        @NamedQuery(name = "Schedule.findBySubscription",
                query = "SELECT s FROM Schedule s WHERE s.subscription.id = :subscriptionId"),
        @NamedQuery(name = "Schedule.findByType", query = "SELECT s FROM Schedule s WHERE s.type = :type"),
        @NamedQuery(name = "Schedule.findByStatus", query = "SELECT s FROM Schedule s WHERE s.status = :status"),
        @NamedQuery(name = "Schedule.findUpcomingSessions",
                query = "SELECT s FROM Schedule s WHERE s.startTime > CURRENT_DATE ORDER BY s.startTime")
})
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "subscription_id", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = false)
    private Subscription subscription;

    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User member;

    @JoinColumn(name = "pt_id", referencedColumnName = "id")
    @ManyToOne
    private User pt;

    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Basic(optional = false)
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Basic(optional = false)
    @Column(name = "type")
    private String type;

    @Basic(optional = false)
    @Column(name = "status")
    private String status;

    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Schedule() {
    }

    public Schedule(Integer id) {
        this.id = id;
    }

    public Schedule(Integer id, Subscription subscription, User member,
                    Date startTime, Date endTime, String type, String status, Date createdAt) {
        this.id = id;
        this.subscription = subscription;
        this.member = member;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public User getPt() {
        return pt;
    }

    public void setPt(User pt) {
        this.pt = pt;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Schedule)) {
            return false;
        }
        Schedule other = (Schedule) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.healthmonitor.pojo.Schedule[ id=" + id + " ]";
    }
}