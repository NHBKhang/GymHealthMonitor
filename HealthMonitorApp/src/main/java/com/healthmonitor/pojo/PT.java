package com.healthmonitor.pojo;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "pt")
@NamedQueries({
        @NamedQuery(name = "PT.findAll", query = "SELECT p FROM PT p"),
        @NamedQuery(name = "PT.findById", query = "SELECT p FROM PT p WHERE p.id = :id"),
        @NamedQuery(name = "PT.findByMajor", query = "SELECT p FROM PT p WHERE p.major = :major")
})
public class PT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "pt_id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "major")
    private String major;

    @JoinColumn(name = "pt_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public PT() {
    }

    public PT(Integer id) {
        this.id = id;
    }

    public PT(Integer id, String major) {
        this.id = id;
        this.major = major;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
