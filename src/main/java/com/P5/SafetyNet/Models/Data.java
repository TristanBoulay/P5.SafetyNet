package com.P5.SafetyNet.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data{

    @JsonProperty("persons")
    private List<Person> persons;

    @JsonProperty("firestations")
    private List<Firestation> firestations;

    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecords;

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public List<Firestation> getFireStationsByAddress(String toto) {
        return firestations.stream().filter((firestation) -> {
            String firestationAddress = firestation.getAddress();
            return firestationAddress.equals(toto);
        }).toList();
    }
}


