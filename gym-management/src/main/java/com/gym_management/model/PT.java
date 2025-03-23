package com.gym_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pt_id")
    private Long ptId;

    @Column(name = "major", nullable = false)
    private String major;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
