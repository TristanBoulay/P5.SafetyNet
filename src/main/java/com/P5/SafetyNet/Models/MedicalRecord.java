package com.P5.SafetyNet.Models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name="medicalrecords")
@Entity
public class MedicalRecord {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="birthdate")
    private String birthdate;

    @Column(name="medications")
    private String medications;


}