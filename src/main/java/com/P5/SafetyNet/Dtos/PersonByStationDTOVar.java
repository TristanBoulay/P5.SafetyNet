package com.P5.SafetyNet.Dtos;

import com.P5.SafetyNet.Models.MedicalRecord;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PersonByStationDTOVar {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private List<String>  allergies;
    private List<String>  medications;
}
