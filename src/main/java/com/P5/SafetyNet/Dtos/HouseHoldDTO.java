package com.P5.SafetyNet.Dtos;

import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.Models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HouseHoldDTO {

    private String address;
    private List<PersonByStationDTOVar> personsByStation;



}
