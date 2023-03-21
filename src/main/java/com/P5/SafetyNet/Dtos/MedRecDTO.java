package com.P5.SafetyNet.Dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MedRecDTO {

    private List<String> medications;


    private List<String> allergies;

    public String toString() {
        return "Medical record of" +"${person.firstName, person.lastName}:"+" "+ this.medications + " " + this.allergies;
    }
}
