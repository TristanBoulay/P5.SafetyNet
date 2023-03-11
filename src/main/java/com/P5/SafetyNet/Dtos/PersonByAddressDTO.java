package com.P5.SafetyNet.Dtos;

import com.P5.SafetyNet.Models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PersonByAddressDTO {

    private List<String> stationNumber;
    private List<Person> personList;


}
