package com.P5.SafetyNet.Controllers;


import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.Models.Person;
import com.P5.SafetyNet.Services.PersonService;
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
@SpringBootTest(classes = PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PersonService personService;

    private com.P5.SafetyNet.Models.Person person;
    private List<com.P5.SafetyNet.Models.Person> persons;


    @BeforeEach
    public void prepareData() throws Exception {
        this.person = new com.P5.SafetyNet.Models.Person();
        MedicalRecord medRec = new MedicalRecord();
        this.person.setId(1L);
        this.person.setFirstName("Jean");
        this.person.setLastName("Paul");
        this.person.setAddress("mentawai");
        this.person.setPhone("0123");
        this.person.setZip("345");
        this.person.setEmail("paul@uber.com");
        this.person.setCity("Nantes");
        this.person.setMedicalRecord(medRec);
        this.persons = new LinkedList<com.P5.SafetyNet.Models.Person>();
        this.persons.add(this.person);

    }

    @Test
    public void testSavePerson() throws Exception {
        String personJson = new ObjectMapper().writeValueAsString(this.person);
        when(personService.savePerson(any())).thenReturn(this.person);

        this.mockMvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON).content(personJson))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(personJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(personService, times(1)).savePerson(any());

    }

    @Test
    public void testGetPersons() throws Exception {
        when(personService.getPersons()).thenReturn(this.persons);
        this.mockMvc.perform(get("/persons").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        
    }

    @Test
    public void testGetPerson() throws Exception {
        when(personService.getPerson(1L)).thenReturn(Optional.of(this.person));
        this.mockMvc.perform(get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jean"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Paul"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("mentawai"));
        verify(personService, times(1)).getPerson(1L);
    }

    @Test
    void testUpdatePerson() throws Exception {
        Person updatedPerson = new Person();
        updatedPerson.setId(1L);
        updatedPerson.setFirstName("Jean");
        updatedPerson.setLastName("Dupont");
        updatedPerson.setAddress("Paris");
        updatedPerson.setPhone("4567");
        updatedPerson.setZip("75001");
        updatedPerson.setEmail("dupont@gmail.com");
        updatedPerson.setCity("Paris");

        when(personService.getPerson(1L)).thenReturn(Optional.of(this.person));
        when(personService.updatePerson(anyLong(), any(Person.class))).thenReturn(updatedPerson);
        String updatedPersonJson = new ObjectMapper().writeValueAsString(updatedPerson);

        this.mockMvc.perform(put("/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPersonJson))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jean"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dupont"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Paris"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Paris"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zip").value("75001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("4567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dupont@gmail.com"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personService, times(1)).updatePerson(eq(1L), argumentCaptor.capture());
        Person savedPerson = argumentCaptor.getValue();
        assertEquals("Jean", savedPerson.getFirstName());
        assertEquals("Dupont", savedPerson.getLastName());
        assertEquals("Paris", savedPerson.getAddress());
        assertEquals("Paris", savedPerson.getCity());
        assertEquals("75001", savedPerson.getZip());
        assertEquals("4567", savedPerson.getPhone());
        assertEquals("dupont@gmail.com", savedPerson.getEmail());
    }

    @Test
    void testDeletePerson() throws Exception {
        doNothing().when(personService).deletePerson(1L);
        this.mockMvc.perform(delete("/persons/1"))
                .andExpect(status().isOk());
        verify(personService, times(1)).deletePerson(1L);
    }
}