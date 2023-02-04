package com.P5.SafetyNet.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="address")
    private String address;
    @Column(name="city")
    private String city;

    @Column(name="zip")
    private String zip;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

}