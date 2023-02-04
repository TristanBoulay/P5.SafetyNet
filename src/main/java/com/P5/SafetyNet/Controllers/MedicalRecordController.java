package com.P5.SafetyNet.Controllers;

import com.P5.SafetyNet.Services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalrecords/{id}")
    public com.P5.SafetyNet.Models.MedicalRecord getMedicalRecord(@PathVariable Long id) {
        return medicalRecordService.getMedicalRecord(id).orElse(null);
    }

    @GetMapping
    public Iterable<com.P5.SafetyNet.Models.MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }

    @DeleteMapping("/medicalrecords/{id}")
    public void deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

    @PostMapping
    public com.P5.SafetyNet.Models.MedicalRecord saveMedicalRecord(@RequestBody com.P5.SafetyNet.Models.MedicalRecord medicalRecord) {
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }
}

