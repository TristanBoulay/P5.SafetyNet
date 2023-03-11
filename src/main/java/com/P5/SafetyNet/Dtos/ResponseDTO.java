package com.P5.SafetyNet.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseDTO {
    List<PersonByStationDTO> persons;
    Long numberOfAdult;
    Long numberOfChildren;
}
