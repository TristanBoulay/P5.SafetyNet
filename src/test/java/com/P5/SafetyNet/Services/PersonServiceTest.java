package com.P5.SafetyNet.Services;

import com.P5.SafetyNet.Dtos.ChildAlertDTO;
import com.P5.SafetyNet.InterfaceRepository.PersonRepository;
import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.Models.Person;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        List<String> medications = new ArrayList<>();
        medications.add("viagra");
        medications.add("prozac");
        medications.add("aspirine");
        List<String> allergies = new ArrayList<>();
        allergies.add("poil de chat");
        allergies.add("pollen");

        MedicalRecord medRec = new MedicalRecord();
        medRec.setFirstName("Jean");
        medRec.setLastName("Paul");
        medRec.setBirthdate("01/01/2000");
        medRec.setMedications(medications);
        medRec.setAllergies(allergies);

        MedicalRecord medRec1 = new MedicalRecord();
        medRec1.setFirstName("Bruno");
        medRec1.setLastName("Padrao");
        medRec1.setBirthdate("01/01/2000");
        medRec1.setMedications(medications);
        medRec1.setAllergies(allergies);

        MedicalRecord medRec2 = new MedicalRecord();
        medRec2.setFirstName("jannot");
        medRec2.setLastName("mendes");
        medRec2.setBirthdate("01/01/2018");
        medRec2.setMedications(medications);
        medRec2.setAllergies(allergies);

        MedicalRecord medRec3 = new MedicalRecord();
        medRec3.setFirstName("Alice");
        medRec3.setLastName("keke");
        medRec3.setBirthdate("01/01/2015");
        medRec3.setMedications(medications);
        medRec3.setAllergies(allergies);


        this.person = new Person();
        this.person.setId((long) 3L);
        this.person.setFirstName("Jean");
        this.person.setLastName("Paul");
        this.person.setAddress("mentawai");
        this.person.setPhone("0123");
        this.person.setZip("345");
        this.person.setEmail("paul@uber.com");
        this.person.setCity("Nantes");
        this.person.setMedicalRecord(medRec);
        this.person.getAge();

        Person parent = new Person();
        parent.setId(5L);
        parent.setFirstName("Bruno");
        parent.setLastName("Padrao");
        parent.setAddress("mentawai");
        parent.setCity("Nantes");
        parent.setZip("345");
        parent.setPhone("0123");
        parent.setEmail("paul@uber.com");
        parent.setMedicalRecord(medRec1);
        parent.getAge();


        Person child1 = new Person();
        child1.setId(6L);
        child1.setFirstName("jannot");
        child1.setLastName("mendes");
        child1.setAddress("mentawai");
        child1.setCity("Nantes");
        child1.setZip("345");
        child1.setPhone("0123");
        child1.setEmail("child1@uber.com");
        child1.setMedicalRecord(medRec2);
        child1.getAge();


        Person child2 = new Person();
        child2.setId(7L);
        child2.setFirstName("Alice");
        child2.setLastName("keke");
        child2.setAddress("mexico");
        child2.setCity("Nantes");
        child2.setZip("345");
        child2.setPhone("0123");
        child2.setEmail("child2@uber.com");
        child2.setMedicalRecord(medRec3);
        child2.getAge();


        this.persons = new LinkedList<>();
        this.persons.add(this.person);
        persons.add(parent);
        persons.add(child1);
        persons.add(child2);
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
        assertEquals(savedPerson.getMedicalRecord(), this.person.getMedicalRecord());
        verify(personRepository, times(1)).save(any());
    }

    @Test
    public void testGetPersons() {
        when(personRepository.findAll()).thenReturn(this.persons);
        List<Person> persons = personService.getPersons();

        assertEquals(4, persons.size());

        Person firstPerson = persons.get(0);

        assertEquals(3, firstPerson.getId());
        assertEquals("Jean", firstPerson.getFirstName());
        assertEquals("Paul", firstPerson.getLastName());
    }

    @Test
    public void testGetPerson () {
        when(personRepository.findById(3L)).thenReturn(Optional.of(this.person));
        Optional<Person> optionalPerson = personService.getPerson(3L);
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
        verify(personRepository, times(1)).findById(3L);
    }

    @Test
    public void testUpdatePerson () {
        List<String> newMedications = new ArrayList<>();
        newMedications.add("v");
        newMedications.add("p");
        newMedications.add("a");
        List<String> newAllergies = new ArrayList<>();
        newAllergies.add("poil de chatte");
        newAllergies.add("pollenus");

        MedicalRecord newMedRec = new MedicalRecord();
        newMedRec.setFirstName("Jean");
        newMedRec.setLastName("Paul");
        newMedRec.setBirthdate("01/01/2000");
        newMedRec.setMedications(newMedications);
        newMedRec.setAllergies(newAllergies);
        newMedRec.setPerson(this.person);

        Person updatedPerson = new Person();
        updatedPerson.setFirstName("Jean");
        updatedPerson.setLastName("Paul");
        updatedPerson.setAddress("Paris");
        updatedPerson.setPhone("4567");
        updatedPerson.setZip("75001");
        updatedPerson.setEmail("dupont@gmail.com");
        updatedPerson.setCity("Paris");
        updatedPerson.setMedicalRecord(newMedRec);
        updatedPerson.getAge();

        when(personRepository.findById(3L)).thenReturn(Optional.of(this.person));
        when(personRepository.save(person)).thenReturn(updatedPerson);

        Person result = personService.updatePerson( 3L, updatedPerson);

        assertEquals("Paris", result.getAddress());
        assertEquals("Paris", result.getCity());
        assertEquals("75001", result.getZip());
        assertEquals("4567", result.getPhone());
        assertEquals("dupont@gmail.com", result.getEmail());
        assertEquals(updatedPerson.getMedicalRecord(), newMedRec);
        assertEquals(23, updatedPerson.getAge());
        verify(personRepository, times(1)).findById(3L);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void testGetPersonByAddress(){
        List<Person> mentawailPeople = persons.stream().filter((person) -> person.getAddress() == "mentawai").toList();

        when(personRepository.findByAddress(any())).thenReturn(mentawailPeople);
        List<Person> result = personService.getPersonByAddress("mentawai");

        for(Person p : result) {
            assertEquals("mentawai", p.getAddress());
        }
    }

    @Test
    public void testGetChildByAddress() {
        List<Person> persons = this.persons;
        when(personRepository.findByAddress(any())).thenReturn(persons);
        List<ChildAlertDTO> children = personService.getChildByAddress("mentawai");

        assertEquals(2, children.size());

        for (ChildAlertDTO child : children) {
            assertTrue(child.getAge() <= 18);
        }
    }
}



