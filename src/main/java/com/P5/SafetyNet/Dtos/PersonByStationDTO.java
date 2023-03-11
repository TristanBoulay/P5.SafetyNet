package com.P5.SafetyNet.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonByStationDTO {
    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.address + " " + this.phoneNumber;
    }
}
