package com.P5.SafetyNet.Models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
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

    @Column(name = "age")
    private int age;

     /*utiliser les annotations JPA pour établir une relation One-to-One entre les entités Person et MedicalRecord :
     @JoinColumn spécifie le nom de la colonne de la table Person -->foreign key pour référencer
     l'entité MedicalRecord, identifiée par id.
      */
     @OneToOne()
     @JsonBackReference
     @JoinColumn(name = "medical_record_id")
     private MedicalRecord medicalRecord;

     @ManyToMany
     @JoinTable(name = "firestations_persons", joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "firestation_id", referencedColumnName = "id"))
     private Set<Firestation> fireStations = new HashSet<Firestation>();

    public int getAge() {
        String birthdateStr = medicalRecord.getBirthdate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
        LocalDate now = LocalDate.now();
        int age = Period.between(birthdate, now).getYears();
        if (now.getMonthValue() == birthdate.getMonthValue() &&
                now.getDayOfMonth() == birthdate.getDayOfMonth()) {
            // Add one day to the birthdate to include it in the age calculation
            birthdate = birthdate.plusDays(1);
            age = Period.between(birthdate, now).getYears();
        }
        return age;

    }

    public void addFireStation(Firestation firestation) {
        fireStations.add(firestation);
    }

    public void setAge(int age) {
       this.age =getAge();
    }

}








