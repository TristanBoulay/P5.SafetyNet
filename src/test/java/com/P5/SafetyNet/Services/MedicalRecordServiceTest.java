package com.P5.SafetyNet.Services;

import com.P5.SafetyNet.InterfaceRepository.MedicalRecordRepository;
import com.P5.SafetyNet.Models.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MedicalRecordServiceTest {
    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private MedicalRecord medicalRecord;

    private List<MedicalRecord> medicalRecordList;

    @BeforeEach
    public void prepareData() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.medicalRecordService = new MedicalRecordService(medicalRecordRepository);
        this.medicalRecordList = new LinkedList<>();
        this.medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setMedications(Arrays.asList("viagra:1kg", "prozac:200mg", "kamagra:200mg"));
        medicalRecord.setAllergies(List.of("aux moules"));
        medicalRecord.setFirstName("JoJo");
        medicalRecord.setLastName("l'asticot");
        medicalRecord.setBirthdate("1969 année érotique");
        medicalRecordList.add(medicalRecord);
    }


    @Test
    public void testGetMedicalRecords() {
        when(medicalRecordRepository.findAll()).thenReturn(this.medicalRecordList);
        Iterable<MedicalRecord> getListMed = medicalRecordService.getMedicalRecords();
        Iterator<MedicalRecord> iterator = getListMed.iterator();
        while (iterator.hasNext()) {
            MedicalRecord nextMed = iterator.next();
            assertEquals(((List<MedicalRecord>) getListMed).size(), 1);
            assertEquals(nextMed.getId(), this.medicalRecord.getId());
            assertEquals(nextMed.getFirstName(), this.medicalRecord.getFirstName());
            assertEquals(nextMed.getLastName(), this.medicalRecord.getLastName());
            assertEquals(nextMed.getAllergies(), this.medicalRecord.getAllergies());
            assertEquals(nextMed.getMedications(), this.medicalRecord.getMedications());

            verify(medicalRecordRepository, times(1)).findAll();
        }
    }

    @Test
    public void testGetMedicalRecord() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(this.medicalRecord));
        Optional<MedicalRecord> optionalMedRec = medicalRecordService.getMedicalRecord(1L);
        assertTrue(optionalMedRec.isPresent());
        MedicalRecord medRec = optionalMedRec.get();
        assertEquals(medRec.getId(), this.medicalRecord.getId());
        assertEquals(medRec.getFirstName(), this.medicalRecord.getFirstName());
        assertEquals(medRec.getLastName(), this.medicalRecord.getLastName());
        assertEquals(medRec.getAllergies(), this.medicalRecord.getAllergies());
        assertEquals(medRec.getMedications(), this.medicalRecord.getMedications());

        verify(medicalRecordRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveMedicalRecord() {
        when(medicalRecordRepository.save(any())).thenReturn(this.medicalRecord);
        MedicalRecord savedMedRec = medicalRecordService.saveMedicalRecord(this.medicalRecord);
        assertEquals(savedMedRec.getId(), this.medicalRecord.getId());
        assertEquals(savedMedRec.getFirstName(), this.medicalRecord.getFirstName());
        assertEquals(savedMedRec.getLastName(), this.medicalRecord.getLastName());
        assertEquals(savedMedRec.getAllergies(), this.medicalRecord.getAllergies());
        assertEquals(savedMedRec.getMedications(), this.medicalRecord.getMedications());

        verify(medicalRecordRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateMedicalRecord() {

        MedicalRecord updatedMedicalRec = new MedicalRecord();
        updatedMedicalRec.setId(1L);
        updatedMedicalRec.setMedications(Arrays.asList("viagra:500g", "strepsil: 40 mg", "prozac:34.45mg"));
        updatedMedicalRec.setAllergies(List.of("poil de chatte"));
        updatedMedicalRec.setBirthdate("1945");
        updatedMedicalRec.setFirstName("JoJo");
        updatedMedicalRec.setLastName("l'asticot");


        when(medicalRecordRepository.findById(medicalRecord.getId())).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordRepository.save(any())).thenReturn(updatedMedicalRec);

        MedicalRecord result = medicalRecordService.updateMedicalRecord(medicalRecord.getId(), updatedMedicalRec);


        assertArrayEquals(new String[]{"viagra:500g", "strepsil: 40 mg", "prozac:34.45mg"}, result.getMedications().toArray());
        assertArrayEquals(new String[]{"poil de chatte"}, result.getAllergies().toArray());
        assertEquals("1945", result.getBirthdate());

        
    }
}
