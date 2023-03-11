package com.P5.SafetyNet.Controllers;


import com.P5.SafetyNet.Models.Firestation;
import com.P5.SafetyNet.Models.Person;
import com.P5.SafetyNet.Services.FirestationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
@SpringBootTest(classes = FirestationController.class)
@AutoConfigureMockMvc
public class FirestationControllerControllerTest {
    @Autowired
   private  MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

    private Firestation firestation;
    private List <Firestation> firestations;

    @BeforeEach
    public void prepareData() throws Exception {
        this.firestation = new Firestation();
        this.firestation.setId((long) 1L);
        this.firestation.setStation("do Joao");
        this.firestation.setAddress("caparicou");
        this.firestations = new LinkedList<Firestation>();
        this.firestations.add(this.firestation);

    }

    @Test
    public void getFirestations() throws  Exception {
        when(firestationService.getFirestations()).thenReturn(this.firestations);
        this.mockMvc.perform(get("/firestations").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
                verify(firestationService, times(1)).getFirestations();

    }

    @Test
    public void testSaveFirestation() throws Exception {
        String firestationJson = new ObjectMapper().writeValueAsString(this.firestation);
        when(firestationService.saveFirestation(any())).thenReturn(this.firestation);

        this.mockMvc.perform(post("/firestations").contentType(MediaType.APPLICATION_JSON).content(firestationJson))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(firestationJson))
                .andExpect(status().isCreated());
        verify(firestationService, times(1)).saveFirestation(any());

    }

    @Test
    public void testGetFirestation() throws Exception {
        when(firestationService.getFirestation(1L)).thenReturn(Optional.of(this.firestation));
        this.mockMvc.perform(get("/firestations/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("caparicou"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value("do Joao"));
        verify(firestationService, times(1)).getFirestation(1L);
    }

    @Test void testUpdateFirestation() throws Exception {
        Firestation updatedFirestation = new Firestation();
        updatedFirestation.setId(1L);
        updatedFirestation.setAddress("ericeira");
        updatedFirestation.setStation("6");


        when(firestationService.getFirestation(1L)).thenReturn(Optional.of(this.firestation));
        when(firestationService.updateFirestation(anyLong(), any(Firestation.class))).thenReturn(updatedFirestation);
        String updatedFirestationJson = new ObjectMapper().writeValueAsString(updatedFirestation);

        this.mockMvc.perform(put("/firestations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedFirestationJson))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("ericeira"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value("6"))

                .andExpect(status().isCreated());

        ArgumentCaptor<Firestation> argumentCaptor = ArgumentCaptor.forClass(Firestation.class) ;
        verify(firestationService, times(1)).updateFirestation(eq(1l), argumentCaptor.capture());
        Firestation savedFirestation = argumentCaptor.getValue();
        assertEquals("ericeira",savedFirestation.getAddress());
        assertEquals("6", savedFirestation.getStation());

    }

    @Test
    void testDeleteFirestation() throws Exception {
        doNothing().when(firestationService).deleteFirestation(1L);
        this.mockMvc.perform(delete("/firestations/1"))
                .andExpect(status().isOk());
        verify(firestationService, times(1)).deleteFirestation(1L);
    }
}
