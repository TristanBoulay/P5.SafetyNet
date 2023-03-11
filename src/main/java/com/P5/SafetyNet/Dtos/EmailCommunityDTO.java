package com.P5.SafetyNet.Dtos;

import com.P5.SafetyNet.Models.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmailCommunityDTO {

    private String cityName;
    private List<String> listOfEmails;


}
