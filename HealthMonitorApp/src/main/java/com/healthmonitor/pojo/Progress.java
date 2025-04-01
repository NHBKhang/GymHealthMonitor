package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "progress")
@NamedQueries({
        @NamedQuery(name = "Progress.findAll", query = "SELECT p FROM Progress p"),
        @NamedQuery(name = "Progress.findById", query = "SELECT p FROM Progress p WHERE p.id = :id"),
        @NamedQuery(name = "Progress.findByMember",
                query = "SELECT p FROM Progress p WHERE p.member.id = :memberId"),
        @NamedQuery(name = "Progress.findByPT",
                query = "SELECT p FROM Progress p WHERE p.pt.id = :ptId"),
        @NamedQuery(name = "Progress.findByDateRange",
                query = "SELECT p FROM Progress p WHERE p.date BETWEEN :startDate AND :endDate"),
        @NamedQuery(name = "Progress.findLatestByMember",
                query = "SELECT p FROM Progress p WHERE p.member.id = :memberId ORDER BY p.date DESC")
})
public class Progress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User member;

    @JoinColumn(name = "pt_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User pt;

    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "weight", nullable = true)
    private Float weight;

    @Column(name = "body_fat", nullable = true)
    private Float bodyFat;

    @Column(name = "muscle_mass", nullable = true)
    private Float muscleMass;

    @Column(name = "notes", nullable = true)
    private String notes;

    public Progress() {
    }

    public Progress(Integer id) {
        this.id = id;
    }

    public Progress(Integer id, User member, User pt, Date date,
                    Float weight, Float bodyFat, Float muscleMass) {
        this.id = id;
        this.member = member;
        this.pt = pt;
        this.date = date;
        this.weight = weight;
        this.bodyFat = bodyFat;
        this.muscleMass = muscleMass;
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

    public User getPt() {
        return pt;
    }

    public void setPt(User pt) {
        this.pt = pt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(Float bodyFat) {
        this.bodyFat = bodyFat;
    }

    public Float getMuscleMass() {
        return muscleMass;
    }

    public void setMuscleMass(Float muscleMass) {
        this.muscleMass = muscleMass;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Progress)) {
            return false;
        }
        Progress other = (Progress) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.healthmonitor.pojo.Progress[ id=" + id + " ]";
    }
}