package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "schedules")
@NamedQueries({
    @NamedQuery(name = "Schedule.findAll", query = "SELECT s FROM Schedule s"),
    @NamedQuery(name = "Schedule.findById", query = "SELECT s FROM Schedule s WHERE s.id = :id"),
    @NamedQuery(name = "Schedule.findByMember",
            query = "SELECT s FROM Schedule s WHERE s.member.id = :memberId"),
    @NamedQuery(name = "Schedule.findByTrainer",
            query = "SELECT s FROM Schedule s WHERE s.trainer.id = :trainerId"),
    @NamedQuery(name = "Schedule.findBySubscription",
            query = "SELECT s FROM Schedule s WHERE s.subscription.id = :subscriptionId"),
    @NamedQuery(name = "Schedule.findByType", query = "SELECT s FROM Schedule s WHERE s.type = :type"),
    @NamedQuery(name = "Schedule.findByStatus", query = "SELECT s FROM Schedule s WHERE s.status = :status"),
    @NamedQuery(name = "Schedule.findUpcomingSessions",
            query = "SELECT s FROM Schedule s WHERE s.startTime > CURRENT_DATE ORDER BY s.startTime")
})
public class Schedule implements Serializable {

    public enum ScheduleStatus {
        UPCOMING("Sắp tới", "badge bg-info"),
        COMPLETED("Hoàn thành", "badge bg-success"),
        CANCELLED("Hủy bỏ", "badge bg-danger"),
        PENDING("Đang chờ", "badge bg-warning"),
        RESCHEDULED("Đã dời", "badge bg-secondary");

        private final String label;
        private final String badgeClass;

        ScheduleStatus(String label, String badgeClass) {
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
    @NotNull(message = "Mã lịch không được để trống!")
    private String code;

    @JoinColumn(name = "subscription_id", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private Subscription subscription;

    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User member;

    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    @ManyToOne
    private User trainer;

    @Column(name = "start_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startTime;

    @Column(name = "end_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endTime;

    @Column(name = "type", nullable = true)
    private String type;

    @Basic(optional = false)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    public Schedule() {
    }

    public Schedule(Integer id) {
        this.id = id;
    }

    public Schedule(Integer id, String code, Subscription subscription, User member, User trainer,
            Date startTime, Date endTime, String type, ScheduleStatus status, Date createdAt,
            String description, Date updatedAt) {
        this.id = id;
        this.code = code;
        this.subscription = subscription;
        this.member = member;
        this.trainer = trainer;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User pt) {
        this.trainer = pt;
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

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
        return "com.healthmonitor.pojo.Schedule[ code=" + code + " ]";
    }
}
