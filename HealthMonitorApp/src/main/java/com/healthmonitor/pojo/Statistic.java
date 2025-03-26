package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "statistics")
@NamedQueries({
        @NamedQuery(name = "Statistic.findAll", query = "SELECT s FROM Statistic s"),
        @NamedQuery(name = "Statistic.findById", query = "SELECT s FROM Statistic s WHERE s.id = :id"),
        @NamedQuery(name = "Statistic.findLatest",
                query = "SELECT s FROM Statistic s ORDER BY s.createdAt DESC"),
        @NamedQuery(name = "Statistic.findByDateRange",
                query = "SELECT s FROM Statistic s WHERE s.createdAt BETWEEN :startDate AND :endDate")
})
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "statistic_id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "total_members")
    private Integer totalMembers;

    @Basic(optional = false)
    @Column(name = "total_revenue")
    private Double totalRevenue;

    @Basic(optional = false)
    @Column(name = "peak_hours")
    private String peakHours;

    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Statistic() {
    }

    public Statistic(Integer id) {
        this.id = id;
    }

    public Statistic(Integer id, Integer totalMembers, Double totalRevenue,
                     String peakHours, Date createdAt) {
        this.id = id;
        this.totalMembers = totalMembers;
        this.totalRevenue = totalRevenue;
        this.peakHours = peakHours;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(Integer totalMembers) {
        this.totalMembers = totalMembers;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getPeakHours() {
        return peakHours;
    }

    public void setPeakHours(String peakHours) {
        this.peakHours = peakHours;
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
        if (!(object instanceof Statistic)) {
            return false;
        }
        Statistic other = (Statistic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.healthmonitor.pojo.Statistic[ id=" + id + " ]";
    }
}