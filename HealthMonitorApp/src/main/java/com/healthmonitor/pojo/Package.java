package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "packages")
@NamedQueries({
        @NamedQuery(name = "Package.findAll", query = "SELECT p FROM Package p"),
        @NamedQuery(name = "Package.findById", query = "SELECT p FROM Package p WHERE p.id = :id"),
        @NamedQuery(name = "Package.findByName", query = "SELECT p FROM Package p WHERE p.name = :name"),
        @NamedQuery(name = "Package.findByDuration", query = "SELECT p FROM Package p WHERE p.duration = :id"),
        @NamedQuery(name = "Package.findByPrice", query = "SELECT p FROM Package p WHERE p.id = :id"),
        @NamedQuery(name = "Package.findByDescription", query = "SELECT p FROM Package p WHERE p.id = :id"),
        @NamedQuery(name = "Package.findByPtSessions", query = "SELECT p FROM Package p WHERE p.id = :id"),
        @NamedQuery(name = "Package.findByStatus", query = "SELECT p FROM Package p WHERE p.id = :id"),
        @NamedQuery(name = "Package.findByCreatedAt", query = "SELECT p FROM Package p WHERE p.id = :id")
})
public class Package implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "duration")
    private String duration;

    @Basic(optional = false)
    @Column(name = "price")
    private Double price;

    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @Column(name = "pt_sessions")
    private Integer ptSessions;

    @Basic(optional = false)
    @Column(name = "status")
    private String status;

    @Basic(optional = false)
    @Column(name = "created_at")
    private String createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gymPackage")
    private Set<Subscription> subscriptionSet;

    public Package() {
    }

    public Package(Integer id) {
        this.id = id;
    }

    public Package(Integer id, String name, String duration, Double price,
                   String description, Integer ptSessions, String status, String createdAt) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.ptSessions = ptSessions;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPtSessions() {
        return ptSessions;
    }

    public void setPtSessions(Integer ptSessions) {
        this.ptSessions = ptSessions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Subscription> getSubscriptionSet() {
        return subscriptionSet;
    }

    public void setSubscriptionSet(Set<Subscription> subscriptionSet) {
        this.subscriptionSet = subscriptionSet;
    }
}