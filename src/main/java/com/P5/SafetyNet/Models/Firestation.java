package com.P5.SafetyNet.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="firestations")

public class Firestation {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="address")
    private String address;



}