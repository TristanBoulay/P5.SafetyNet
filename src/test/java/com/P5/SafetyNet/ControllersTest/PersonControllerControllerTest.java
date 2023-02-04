package com.P5.SafetyNet.ControllersTest;

import com.P5.SafetyNet.Controllers.PersonController;


import com.P5.SafetyNet.Services.PersonService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.LinkedList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@SpringBootTest(classes = Person.class)
@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerControllerTest {


    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private PersonService personService;

    private com.P5.SafetyNet.Models.Person person;
    private List<com.P5.SafetyNet.Models.Person> persons;
   
    @BeforeEach
    public void prepareData() throws Exception{
        this.person = new com.P5.SafetyNet.Models.Person();
        this.person.setId((long) 1L);
        this.person.setFirstName("Jean");
        this.person.setLastName("Paul");
        this.person.setAddress("mentawai");
        this.person.setPhone("0123");
        this.person.setZip("345");
        this.person.setEmail("paul@uber.com");
        this.person.setCity("Nantes");
        this.persons = new LinkedList<com.P5.SafetyNet.Models.Person>();
        this.persons.add(this.person);

    }
    @Test
    public void testGetPersons() throws Exception {
        when(personService.getPersons()).thenReturn(this.persons);
        this.mockMvc.perform(get("/persons").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
        verify(personService, times(1)).getPersons();
    }
    @Test
    public void testGetPerson() throws Exception {
        when(personService.getPerson(1L)).thenReturn(Optional.of(this.person));
        this.mockMvc.perform(get("/person/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.firstName", is("Jean")));
        verify(personService, times(1)).getPerson(1L);
    }
}