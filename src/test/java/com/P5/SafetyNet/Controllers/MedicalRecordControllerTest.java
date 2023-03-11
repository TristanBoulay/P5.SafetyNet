package com.P5.SafetyNet.Controllers;

import com.P5.SafetyNet.InterfaceRepository.MedicalRecordRepository;
import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.Services.MedicalRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@EnableWebMvc
@SpringBootTest(classes = MedicalRecordController.class)
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {
    @MockBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MockMvc mockMvc;

    private MedicalRecord medicalRecord;

    private List<MedicalRecord> medicalRecords;
    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    @BeforeEach
    public void prepareData() throws Exception {
        this.medicalRecords = new LinkedList<>();
        this.medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setMedications(Arrays.asList("viagra:1kg", "prozac:200mg", "kamagra:200mg"));
        medicalRecord.setAllergies(Arrays.asList("aux moules"));
        medicalRecord.setFirstName("JoJo");
        medicalRecord.setLastName("l'asticot");
        medicalRecord.setBirthdate("1969 année érotique");
        medicalRecords.add(medicalRecord);
    }

    @Test
    public void testGetMedicalRecords() throws Exception{
        when(medicalRecordService.getMedicalRecords()).thenReturn(medicalRecords);
        this.mockMvc.perform(get("/medicalrecords").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(medicalRecordService, times(1)).getMedicalRecords();
    }

    @Test
    public void testGetMedicalRecord() throws  Exception{
        when(medicalRecordService.getMedicalRecord(1L)).thenReturn(Optional.of(this.medicalRecord));
        this.mockMvc.perform(get("/medicalrecords/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medications").isArray())
                .andExpect(jsonPath("$.medications", Matchers.hasItems("viagra:1kg", "prozac:200mg", "kamagra:200mg")))
                .andExpect(jsonPath("$.allergies").isArray())
                .andExpect(jsonPath("$.allergies", Matchers.hasItems("aux moules")))
                .andExpect(jsonPath("$.firstName").value("JoJo"))
                .andExpect(jsonPath("$.lastName").value("l'asticot"))
                .andExpect(jsonPath("$.birthdate").value("1969 année érotique"));
    }

    @Test
    public void testSaveMedicalRecord() throws Exception{
        String medicalRecJson = new ObjectMapper().writeValueAsString(medicalRecord);
        when(medicalRecordService.saveMedicalRecord(any())).thenReturn(this.medicalRecord);
        this.mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(medicalRecJson))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(medicalRecJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception{
        MedicalRecord updatedMedicalRec = new MedicalRecord();
        updatedMedicalRec.setId(1L);
        updatedMedicalRec.setMedications(Arrays.asList("viagra:500g, strepsil: 40 mg","prozac:34.45mg"));
        updatedMedicalRec.setAllergies(Arrays.asList("poil de chatte"));
        updatedMedicalRec.setBirthdate("1945");
        updatedMedicalRec.setFirstName("Jojo");
        updatedMedicalRec.setLastName("l'asticot");


        when(medicalRecordService.getMedicalRecord(1L)).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordService.updateMedicalRecord(anyLong(),any(MedicalRecord.class))).thenReturn(updatedMedicalRec);
        String updateMedicalRecJson = new ObjectMapper().writeValueAsString(updatedMedicalRec);
        this.mockMvc.perform(put("/medicalRecord/1").contentType(MediaType.APPLICATION_JSON).content(updateMedicalRecJson))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Jojo"))
                .andExpect(jsonPath("$.lastName").value("l'asticot"))
                .andExpect(jsonPath("$.birthdate").value("1945"))
                .andExpect(jsonPath("$.medications", Matchers.hasItems("viagra:500g, strepsil: 40 mg","prozac:34.45mg")))
                .andExpect(jsonPath("$.allergies", Matchers.hasItems("poil de chatte")))
                .andExpect(status().isCreated());

    }
}

