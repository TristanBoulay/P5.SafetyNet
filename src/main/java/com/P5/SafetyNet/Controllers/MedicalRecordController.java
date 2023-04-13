package com.P5.SafetyNet.Controllers;

import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.Services.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;


    @GetMapping("/medicalrecords/{id}")
    public MedicalRecord getMedicalRecord(@PathVariable Long id) {
        log.info("GET request received for medical record with id {}", id);
        return medicalRecordService.getMedicalRecord(id).orElse(null);
    }

    @GetMapping("/medicalrecords")
    public Iterable<MedicalRecord> getMedicalRecords() {
        log.info("GET request received for all medical records");
        return medicalRecordService.getMedicalRecords();
    }

    @DeleteMapping("/medicalrecords/{id}")
    public void deleteMedicalRecord(@PathVariable Long id) {
        log.info("DELETE request received for medical record with id {}", id);
        medicalRecordService.deleteMedicalRecord(id);
    }

    @PostMapping(value = "/medicalRecord", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicalRecord> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("POST request received to save medical record for person with id {}", medicalRecord.getPerson());
        MedicalRecord savedMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(savedMedicalRecord, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/medicalRecord/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord medicalRecord) {
        try {
            log.info("PUT request received to update medical record with id {}", id);
            MedicalRecord updatedMedicRecord = medicalRecordService.updateMedicalRecord(id, medicalRecord);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(updatedMedicRecord, headers, HttpStatus.CREATED);
        } catch (Exception exception) {
            log.error("An error occurred while updating medical record with id {}: {}", id, exception.getMessage());
            if ("lol tu veux changer ton nom ?".equals(exception.getMessage())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            throw exception;

        }
    }
}
