package com.healthmonitor.pojo;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "members")
@NamedQueries({
    @NamedQuery(name = "Member.findAll", query = "SELECT m FROM Member m"),
    @NamedQuery(name = "Member.findById", query = "SELECT m FROM Member m WHERE m.id = :id"),
    @NamedQuery(name = "Member.findByHeight", query = "SELECT m FROM Member m WHERE m.height = :height"),
    @NamedQuery(name = "Member.findByWeight", query = "SELECT m FROM Member m WHERE m.weight = :weight"),
    @NamedQuery(name = "Member.findByFitnessGoal", query = "SELECT m FROM Member m WHERE m.fitnessGoal = :fitnessGoal")
})
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "height", nullable = true)
    private Float height;

    @Basic(optional = false)
    @Column(name = "weight", nullable = true)
    private Float weight;

    @Basic(optional = false)
    @Column(name = "fitness_goal", nullable = true)
    private String fitnessGoal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    public Member() {
    }

    public Member(Integer id) {
        this.id = id;
    }

    public Member(User user) {
        this.user = user;
    }

    public Member(Integer id, Float height, Float weight, String fitnessGoal) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.fitnessGoal = fitnessGoal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getMember() == null) {
            user.setMember(this);
        }
    }
}
