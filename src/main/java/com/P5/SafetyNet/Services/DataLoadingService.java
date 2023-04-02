package com.P5.SafetyNet.Services;

import com.P5.SafetyNet.InterfaceRepository.FirestationRepository;
import com.P5.SafetyNet.InterfaceRepository.MedicalRecordRepository;
import com.P5.SafetyNet.InterfaceRepository.PersonRepository;
import com.P5.SafetyNet.Models.Firestation;
import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.Models.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Data
@Service
public class DataLoadingService {
    @Autowired
    private final PersonRepository personRepository;
    @Autowired
    private final MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private final FirestationRepository firestationRepository;


    @Value("/Users/tristanboulay/eclipse-workspace/SafetyNet/src/main/resources/data.json")
    String dataJsonPath;

    @PostConstruct
    public void loadData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(dataJsonPath);
        com.P5.SafetyNet.Models.Data data = mapper.readValue(file, com.P5.SafetyNet.Models.Data.class);

        List<MedicalRecord> medicalRecords = data.getMedicalRecords();

        for (MedicalRecord medicalRecord : medicalRecords) {
            medicalRecordRepository.save(medicalRecord);
        }

        List<Firestation> fireStations = data.getFirestations();

        for (Firestation firestation : fireStations) {
            firestationRepository.save(firestation);
        }

        List<Person> persons = data.getPersons();

        for (Person person : persons) {
            MedicalRecord medicalRecordLinked = medicalRecordRepository.searchByFirstAndLastName(person.getFirstName(), person.getLastName());
            person.setMedicalRecord(medicalRecordLinked);

            List<Firestation> fireStationsLinked = data.getFireStationsByAddress(person.getAddress());

            for (Firestation fireStation : fireStationsLinked) {
                person.addFireStation(fireStation);
            }

            personRepository.save(person);
        }

    }
}
