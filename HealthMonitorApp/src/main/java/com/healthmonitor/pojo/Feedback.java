package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "feedback")
@NamedQueries({
        @NamedQuery(name = "Feedback.findAll", query = "SELECT f FROM Feedback f"),
        @NamedQuery(name = "Feedback.findById", query = "SELECT f FROM Feedback f WHERE f.id = :id"),
        @NamedQuery(name = "Feedback.findByMember",
                query = "SELECT f FROM Feedback f WHERE f.user.id = :userId"),
        @NamedQuery(name = "Feedback.findByRating",
                query = "SELECT f FROM Feedback f WHERE f.rating = :rating"),
        @NamedQuery(name = "Feedback.findByRatingGreaterThan",
                query = "SELECT f FROM Feedback f WHERE f.rating >= :minRating")
})
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

    @Basic(optional = false)
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Basic(optional = false)
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Feedback() {
    }

    public Feedback(Integer id) {
        this.id = id;
    }

    public Feedback(Integer id, User user, Integer rating, Date createdAt) {
        this.id = id;
        this.user = user;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User geUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        if (!(object instanceof Feedback)) {
            return false;
        }
        Feedback other = (Feedback) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.healthmonitor.pojo.Feedback[ id=" + id + " ]";
    }
}