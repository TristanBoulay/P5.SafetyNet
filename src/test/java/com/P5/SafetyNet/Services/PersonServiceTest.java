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
        medRec.setPerson(this.person);

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
        parent.setFirstName("Jean");
        parent.setLastName("Paul");
        parent.setAddress("mentawai");
        parent.setCity("Nantes");
        parent.setZip("345");
        parent.setPhone("0123");
        parent.setEmail("paul@uber.com");
        parent.setMedicalRecord(medRec);
        parent.setAge(30);

        Person child1 = new Person();
        child1.setId(6L);
        child1.setFirstName("Jean");
        child1.setLastName("Paul");
        child1.setAddress("mentawai");
        child1.setCity("Nantes");
        child1.setZip("345");
        child1.setPhone("0123");
        child1.setEmail("child1@uber.com");
        child1.setMedicalRecord(medRec);
        child1.setAge(5);


        Person child2 = new Person();
        child2.setId(7L);
        child2.setFirstName("Child2");
        child2.setLastName("Paul");
        child2.setAddress("mexico");
        child2.setCity("Nantes");
        child2.setZip("345");
        child2.setPhone("0123");
        child2.setEmail("child2@uber.com");
        child2.setMedicalRecord(medRec);
        child2.setAge(8);


        this.persons = new LinkedList<>();
        this.persons.add(this.person);
        persons.add(parent);
        persons.add(child1);
        persons.add(child2);
        System.out.println(persons);
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
            when(personRepository.findByAddress(any())).thenReturn(persons);
            List<Person> result = personService.getPersonByAddress("mentawai");
                    for(Person p : result) {
                        assertEquals("mentawai", p.getAddress());

                    }
        }

    @Test
    public void testGetChildByAddress() {
        List<Person> persons = this.persons;
        when(personRepository.findByAddress(person.getAddress())).thenReturn(persons);
        List<Person> children = new ArrayList<>();
             for(Person p : persons) {
               ChildAlertDTO newChild =  new ChildAlertDTO();
               newChild = personService.getChildByAddress(p.getAddress());
                 children.add(newChild);

        assertEquals(2, children.size());
        assertTrue(children.contains(p.getAge()<= 18));
             }
    }
    }


