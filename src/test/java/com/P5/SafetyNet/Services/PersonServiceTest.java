package com.P5.SafetyNet.Services;

import com.P5.SafetyNet.InterfaceRepository.PersonRepository;
import com.P5.SafetyNet.Models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    private Person person;
    private List<Person> persons;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.personService = new PersonService(personRepository);

        this.person = new Person();
        this.person.setId((long) 1L);
        this.person.setFirstName("Jean");
        this.person.setLastName("Paul");
        this.person.setAddress("mentawai");
        this.person.setPhone("0123");
        this.person.setZip("345");
        this.person.setEmail("paul@uber.com");
        this.person.setCity("Nantes");

        this.persons = new LinkedList<>();
        this.persons.add(this.person);
    }

    @Test
    public void testSavePerson() {
        when(personRepository.save(any())).thenReturn(this.person);
        Person savedPerson = personService.savePerson(this.person);
        assertEquals(savedPerson.getId(), this.person.getId());
        assertEquals(savedPerson.getFirstName(), this.person.getFirstName());
        assertEquals(savedPerson.getLastName(), this.person.getLastName());
        assertEquals(savedPerson.getAddress(), this.person.getAddress());
        assertEquals(savedPerson.getPhone(), this.person.getPhone());
        assertEquals(savedPerson.getZip(), this.person.getZip());
        assertEquals(savedPerson.getEmail(), this.person.getEmail());
        assertEquals(savedPerson.getCity(), this.person.getCity());
        verify(personRepository, times(1)).save(any());
    }

    @Test
    public void testGetPersons() {
        when(personRepository.findAll()).thenReturn(this.persons);
        Iterable<Person> persons = personService.getPersons();
        Iterator<Person> iterator = persons.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            assertEquals(((List<Person>) persons).size(), 1);
            assertEquals(person.getId(), this.person.getId());
            assertEquals(person.getFirstName(), this.person.getFirstName());
            assertEquals(person.getLastName(), this.person.getLastName());
            assertEquals(person.getAddress(), this.person.getAddress());
            assertEquals(person.getPhone(), this.person.getPhone());
            assertEquals(person.getZip(), this.person.getZip());
            assertEquals(person.getEmail(), this.person.getEmail());
            assertEquals(person.getCity(), this.person.getCity());
            verify(personRepository, times(1)).findAll();
        }
    }

        @Test
        public void testGetPerson () {
            when(personRepository.findById(1L)).thenReturn(Optional.of(this.person));
            Optional<Person> optionalPerson = personService.getPerson(1L);
            assertEquals(optionalPerson.isPresent(), true);
            Person person = optionalPerson.get();
            assertEquals(person.getId(), this.person.getId());
            assertEquals(person.getFirstName(), this.person.getFirstName());
            assertEquals(person.getLastName(), this.person.getLastName());
            assertEquals(person.getAddress(), this.person.getAddress());
            assertEquals(person.getPhone(), this.person.getPhone());
            assertEquals(person.getZip(), this.person.getZip());
            assertEquals(person.getEmail(), this.person.getEmail());
            assertEquals(person.getCity(), this.person.getCity());
            verify(personRepository, times(1)).findById(1L);
        }

        @Test
        public void testUpdatePerson () {

            Person updatedPerson = new Person();
            updatedPerson.setId(1L);
            updatedPerson.setFirstName("Jean");
            updatedPerson.setLastName("Paul");
            updatedPerson.setAddress("Paris");
            updatedPerson.setPhone("4567");
            updatedPerson.setZip("75001");
            updatedPerson.setEmail("dupont@gmail.com");
            updatedPerson.setCity("Paris");

            when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
            when(personRepository.save(updatedPerson)).thenReturn(updatedPerson);

            Person result = personService.updatePerson(person.getId(), updatedPerson);

            assertEquals("Jean", result.getFirstName());
            assertEquals("Paul", result.getLastName());
            assertEquals("Paris", result.getAddress());
            assertEquals("Paris", result.getCity());
            assertEquals("75001", result.getZip());
            assertEquals("4567", result.getPhone());
            assertEquals("dupont@gmail.com", result.getEmail());
            verify(personRepository, times(1)).findById(person.getId());
            verify(personRepository, times(1)).save(updatedPerson);
        }
    }


