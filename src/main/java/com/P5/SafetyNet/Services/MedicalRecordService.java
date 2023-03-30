package com.P5.SafetyNet.Services;

import com.P5.SafetyNet.InterfaceRepository.MedicalRecordRepository;
import com.P5.SafetyNet.Models.MedicalRecord;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class MedicalRecordService {


    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Optional<MedicalRecord> getMedicalRecord(final Long id) {
        return medicalRecordRepository.findById(id);
    }

    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public void deleteMedicalRecord(final Long id) {
        medicalRecordRepository.deleteById(id);
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {

        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(long id, MedicalRecord medicalRecordNewData) {
        Optional<MedicalRecord> optionalMedicalRecord = medicalRecordRepository.findById(id);
        if (optionalMedicalRecord.isPresent()) {
            MedicalRecord foundMedRec = optionalMedicalRecord.get();

            if (!foundMedRec.getFirstName().equals(medicalRecordNewData.getFirstName()) ||
                    !foundMedRec.getLastName().equals(medicalRecordNewData.getLastName())) {
                throw new IllegalArgumentException("lol tu veux changer ton nom ?tu peux pas");

            }

            foundMedRec.setId(medicalRecordNewData.getId());
            foundMedRec.setMedications(medicalRecordNewData.getMedications());
            foundMedRec.setAllergies(medicalRecordNewData.getAllergies());
            foundMedRec.setBirthdate(medicalRecordNewData.getBirthdate());

            return medicalRecordRepository.save(foundMedRec);
        } else {
            throw new IllegalArgumentException("Medical record with id " + id + " not found");
        }
    }
}


