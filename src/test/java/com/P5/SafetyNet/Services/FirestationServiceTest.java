package com.P5.SafetyNet.Services;

import com.P5.SafetyNet.Dtos.PersonByStationDTOList;
import com.P5.SafetyNet.InterfaceRepository.FirestationRepository;
import com.P5.SafetyNet.Models.Firestation;
import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.Models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class FirestationServiceTest {
    private FirestationService firestationService;

    private Firestation firestation;

    private List<Firestation> firestations;

    @Mock
    private FirestationRepository firestationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.firestationService = new FirestationService(firestationRepository);
        Set<Person> assignedPersons = new HashSet<>();
        MedicalRecord medRec = new MedicalRecord();
        List<String> medications = new ArrayList<>();
        medications.add("viagra");
        medications.add("prozac");
        medications.add("aspirine");
        List<String> allergies = new ArrayList<>();
        allergies.add("poil de chat");
        allergies.add("pollen");
        medRec.setFirstName("Jean");
        medRec.setLastName("Paul");
        medRec.setBirthdate("01/01/2000");
        medRec.setMedications(medications);
        medRec.setAllergies(allergies);
        Person personTest = new Person();
        personTest.setId(3L);
        personTest.setFirstName("Jean");
        personTest.setLastName("Paul");
        personTest.setAddress("mentawai");
        personTest.setPhone("0123");
        personTest.setZip("345");
        personTest.setEmail("paul@uber.com");
        personTest.setCity("Nantes");
        personTest.setMedicalRecord(medRec);
        personTest.getAge();
        assignedPersons.add(personTest);

        this.firestation = new Firestation();
        this.firestation.setId(1L);
        this.firestation.setAddress("Bali");
        this.firestation.setStation(9L);
        this.firestation.setAssignedPersons(assignedPersons);

        this.firestations = new LinkedList<>();
        this.firestations.add(firestation);
    }

    @Test
    public void getFirestations() throws Exception {
        when(firestationRepository.findAll()).thenReturn(this.firestations);
        Iterable<Firestation> firestationList = firestationService.getFirestations();
        Iterator<Firestation> firestationIterator = firestationList.iterator();
        while (firestationIterator.hasNext()) {
            Firestation nextFirestation = firestationIterator.next();
            assertEquals(nextFirestation.getId(), this.firestation.getId());
            assertEquals(nextFirestation.getAddress(), this.firestation.getAddress());
            assertEquals(nextFirestation.getStation(), this.firestation.getStation());

        }
    }

    @Test
    public void testGetFirestation() {
        when(firestationRepository.findById(1L)).thenReturn(Optional.of(this.firestation));
        Optional<Firestation> optionalFirestation = firestationService.getFirestation(1L);
        assertTrue(optionalFirestation.isPresent());
        Firestation getFirestation = optionalFirestation.get();
        assertEquals(getFirestation.getId(), this.firestation.getId());
        assertEquals(getFirestation.getAddress(), this.firestation.getAddress());
        assertEquals(getFirestation.getStation(), this.firestation.getStation());


        verify(firestationRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveFirestation() {
        when(firestationRepository.save(any())).thenReturn(this.firestation);
        Firestation savedFirestation = firestationService.saveFirestation(this.firestation);
        assertEquals(savedFirestation.getId(), this.firestation.getId());
        assertEquals(savedFirestation.getAddress(), this.firestation.getAddress());
        assertEquals(savedFirestation.getStation(), this.firestation.getStation());


        verify(firestationRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateFirestation() {

        Firestation updatedFirestation = new Firestation();
        updatedFirestation.setId(1L);
        updatedFirestation.setAddress("1945");
        updatedFirestation.setStation(7L);

        when(firestationRepository.findById(any())).thenReturn(Optional.of(firestation));
        when(firestationRepository.save(any())).thenReturn(updatedFirestation);

        Firestation result = firestationService.updateFirestation(firestation.getId(), updatedFirestation);
        assertEquals("1945", result.getAddress());
        assertEquals(7, result.getStation());
    }

    @Test
    public void testReturnPersonsByFireStation() {
        when(firestationRepository.findByStation(any())).thenReturn(firestations);
        PersonByStationDTOList personByStationDTOList = firestationService.returnPersonsByFireStation(9L);
        assertEquals(9L, firestation.getStation());

        // List<Person>
        // List<PersonByStationDTO>
        assertEquals(firestation.getAssignedPersons().size(), personByStationDTOList.getPersons().size());

        assertEquals(firestation.getAssignedPersons().stream().toList().get(0).getFirstName(), personByStationDTOList.getPersons().get(0).getFirstName());

    }
}
