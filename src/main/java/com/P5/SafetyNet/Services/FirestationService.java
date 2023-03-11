package com.P5.SafetyNet.Services;

import com.P5.SafetyNet.Dtos.PersonByAddressDTO;
import com.P5.SafetyNet.Dtos.PersonByStationDTO;
import com.P5.SafetyNet.Dtos.ResponseDTO;
import com.P5.SafetyNet.InterfaceRepository.FirestationRepository;
import com.P5.SafetyNet.InterfaceRepository.PersonRepository;
import com.P5.SafetyNet.Models.Firestation;
import com.P5.SafetyNet.Models.Person;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@Service
public class FirestationService {


    private FirestationRepository firestationRepository;
    private final PersonRepository personRepository;

    @Autowired
    public FirestationService( FirestationRepository firestationRepository,
                               PersonRepository personRepository){
        this.firestationRepository = firestationRepository;
        this.personRepository = personRepository;
    }

    public Optional<Firestation> getFirestation(final Long id) {
        return firestationRepository.findById(id);
    }

    public Iterable<Firestation> getFirestations() {
        return firestationRepository.findAll();
    }

    public void deleteFirestation(final Long id) {
        firestationRepository.deleteById(id);
    }

    public Firestation saveFirestation(Firestation firestation) {

        return firestationRepository.save(firestation);
    }

    public Firestation updateFirestation(Long id, Firestation newFirestationData) {
        Optional<Firestation> optionalFirestation = firestationRepository.findById(id);
        if (optionalFirestation.isPresent()) {
            Firestation firestationToUpdate = optionalFirestation.get();
            firestationToUpdate.setStation(newFirestationData.getStation());
            firestationToUpdate.setAddress(newFirestationData.getAddress());
            return firestationRepository.save(firestationToUpdate);
        } else {
            throw new IllegalArgumentException("Firestation with id " + id + " not found");
        }
    }

    public List<Firestation> findByStation(String stationId){
        return firestationRepository.findByStation(stationId);
    }

    public ResponseDTO returnListByFireStation(String stationId) {
        List<Firestation> firestationsInStation = findByStation(stationId);
        List<Person> personsInStation= new LinkedList<>();

        for (Firestation firestation : firestationsInStation){
            Set<Person> personsInFirestation = firestation.getAssignedPersons();

            personsInStation.addAll(personsInFirestation);
        }

        List<PersonByStationDTO> personListByStation = personsInStation.stream()
                .map(person -> {
                    return new PersonByStationDTO(person.getFirstName(),person.getLastName(),person.getAddress(), person.getPhone());
                }).toList();

        Long numberOfAdult = personsInStation.stream().filter(person -> person.getAge() > 18).count();
        Long numberOfChildren = personsInStation.stream().filter(person -> person.getAge() <= 18).count();

        return new ResponseDTO(personListByStation, numberOfAdult, numberOfChildren);


    }


}
