package com.P5.SafetyNet.Dtos;

import com.P5.SafetyNet.Models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TelephoneByAddressDTO {
    private String stationNumber;

    private List<String> phoneNumberList;
}
