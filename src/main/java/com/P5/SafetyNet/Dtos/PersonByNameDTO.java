package com.P5.SafetyNet.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonByNameDTO {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;

    private MedRecDTO medRecDTO;
}
