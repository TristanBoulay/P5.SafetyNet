package com.P5.SafetyNet.Dtos;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ChildAlertDTO {
    private String firstName;
    private String lastName;
    private int age;
    private List<String> otherHouseholdMembers;

    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.age + " " + this.otherHouseholdMembers;
    }


    // constructor, getters and setters
}

