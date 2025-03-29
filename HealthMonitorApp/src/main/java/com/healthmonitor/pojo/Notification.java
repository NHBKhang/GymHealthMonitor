package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "notifications")
@NamedQueries({
        @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
        @NamedQuery(name = "Notification.findById", query = "SELECT n FROM Notification n WHERE n.id = :id"),
        @NamedQuery(name = "Notification.findByUser",
                query = "SELECT n FROM Notification n WHERE n.user.id = :userId"),
        @NamedQuery(name = "Notification.findByType",
                query = "SELECT n FROM Notification n WHERE n.type = :type"),
        @NamedQuery(name = "Notification.findByStatus",
                query = "SELECT n FROM Notification n WHERE n.status = :status"),
        @NamedQuery(name = "Notification.findUnreadByUser",
                query = "SELECT n FROM Notification n WHERE n.user.id = :userId AND n.status = 'unread'"),
        @NamedQuery(name = "Notification.findRecentNotifications",
                query = "SELECT n FROM Notification n WHERE n.createdAt >= :date ORDER BY n.createdAt DESC")
})
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

    @Basic(optional = false)
    @Column(name = "message")
    private String message;

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

    public Notification() {
        this.status = "unread";
    }

    public Notification(Integer id) {
        this.id = id;
    }

    public Notification(Integer id, User user, String message, String type, Date createdAt) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
        this.status = "unread";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.healthmonitor.pojo.Notification[ id=" + id + " ]";
    }
}