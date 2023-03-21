package com.P5.SafetyNet.Models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="firestations")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Firestation {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="address")
    private String address;

    @Column(name="station")
    private Long station;

    @ManyToMany (mappedBy = "fireStations")
    private Set<Person> assignedPersons = new HashSet<Person>();

    public void addPerson(Person person) {
        assignedPersons.add(person);

        person.getFireStations().add(this);
    }
}