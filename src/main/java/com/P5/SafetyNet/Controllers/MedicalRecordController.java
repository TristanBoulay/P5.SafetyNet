package com.P5.SafetyNet.Controllers;

import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.Models.Person;
import com.P5.SafetyNet.Services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalrecords/{id}")
    public com.P5.SafetyNet.Models.MedicalRecord getMedicalRecord(@PathVariable Long id) {
        return medicalRecordService.getMedicalRecord(id).orElse(null);
    }

    @GetMapping("/medicalrecords")
    public Iterable<com.P5.SafetyNet.Models.MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }

    @DeleteMapping("/medicalrecords/{id}")
    public void deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

    @PostMapping(value="/medicalRecord",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicalRecord> saveMedicalRecord(@RequestBody com.P5.SafetyNet.Models.MedicalRecord medicalRecord) {
        MedicalRecord savedMedicalRecord = medicalRecordService.saveMedicalRecord(medicalRecord);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(savedMedicalRecord, headers, HttpStatus.CREATED);
    }

    @PutMapping(value="/medicalRecord/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord medicalRecord) {
        try {
            MedicalRecord updatedMedicRecord = medicalRecordService.updateMedicalRecord(id, medicalRecord);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(updatedMedicRecord, headers, HttpStatus.CREATED);
        } catch (Exception exception) {
            if ("lol tu veux changer ton nom ?".equals(exception.getMessage())) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            throw exception;

        }
    }
}

