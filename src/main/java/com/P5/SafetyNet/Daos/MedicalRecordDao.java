package com.P5.SafetyNet.Daos;

import com.P5.SafetyNet.InterfaceRepository.MedicalRecordRepository;
import com.P5.SafetyNet.Models.Data;
import com.P5.SafetyNet.Models.MedicalRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;




@Repository
public class MedicalRecordDao {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Value("$data.json.path")
    private String dataJsonPath;

    @PostConstruct
    public void loadData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Data data = mapper.readValue(new File(dataJsonPath), Data.class);

        for (MedicalRecord medicalRecord : data.getMedicalRecords()) {
            medicalRecordRepository.save(medicalRecord);
        }
    }




    public Optional<MedicalRecord> getMedicalRecord(final Long id) {
        return medicalRecordRepository.findById(id);
    }

    public Iterable<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.save(medicalRecord);
    }

    public void deleteMedicalRecord(final Long id) {
        medicalRecordRepository.deleteById(id);
    }
}

