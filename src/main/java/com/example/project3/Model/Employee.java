package com.example.project3.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private double salary;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;
}
