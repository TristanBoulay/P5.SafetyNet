package com.P5.SafetyNet.Services;

import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.InterfaceRepository.MedicalRecordRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class MedicalRecordService {

        @Autowired
        private MedicalRecordRepository medicalRecordRepository;

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

            return  medicalRecordRepository.save(medicalRecord);
        }

}
