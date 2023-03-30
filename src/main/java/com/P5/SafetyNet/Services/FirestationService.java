package com.P5.SafetyNet.Services;

import com.P5.SafetyNet.Dtos.HouseHoldDTO;
import com.P5.SafetyNet.Dtos.PersonByStationDTO;
import com.P5.SafetyNet.Dtos.PersonByStationDTOList;
import com.P5.SafetyNet.Dtos.PersonByStationDTOVar;
import com.P5.SafetyNet.InterfaceRepository.FirestationRepository;
import com.P5.SafetyNet.Models.Firestation;
import com.P5.SafetyNet.Models.MedicalRecord;
import com.P5.SafetyNet.Models.Person;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@Service
public class FirestationService {


    private FirestationRepository firestationRepository;


    @Autowired
    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;

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

    public List<Firestation> findByStation(Long station) {
        return firestationRepository.findByStation(station);
    }

    public PersonByStationDTOList returnPersonsByFireStation(Long station) {
        List<Firestation> firestationsByStation = findByStation(station);
        List<Person> personsInStation = new LinkedList<>();

        for (Firestation firestation : firestationsByStation) {
            Set<Person> personsInFirestation = firestation.getAssignedPersons();

            personsInStation.addAll(personsInFirestation);
        }

        List<PersonByStationDTO> listOfPersonByStation = personsInStation.stream().map(person -> {
            return new PersonByStationDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
        }).toList();

        Long numberOfAdult = personsInStation.stream().filter(person -> person.getAge() > 18).count();
        Long numberOfChildren = personsInStation.stream().filter(person -> person.getAge() <= 18).count();

        return new PersonByStationDTOList(listOfPersonByStation, numberOfAdult, numberOfChildren);


    }

    public HashMap<Long, List<HouseHoldDTO>> getHouseHoldsByStation(List<Long> stations) {
        HashMap<Long, List<HouseHoldDTO>> stationList = new HashMap<Long, List<HouseHoldDTO>>();
        List<PersonByStationDTOVar> personByStationDTOList;

        for (Long station : stations) {
            List<Firestation> firestationsByStation = findByStation(station);
            List<HouseHoldDTO> houseHoldDTOList = new LinkedList<>();

            for (Firestation firestation : firestationsByStation) {
                String address = firestation.getAddress();
                Set<Person> getPersonsFromFirestations = firestation.getAssignedPersons();

                personByStationDTOList = getPersonsFromFirestations.stream().map((person) -> {
                    MedicalRecord medicalRecord = person.getMedicalRecord();
                    List<String> allergies = medicalRecord.getAllergies().isEmpty() ? new ArrayList<>() : medicalRecord.getAllergies();
                    List<String> medications = medicalRecord.getMedications().isEmpty() ? new ArrayList<>() : medicalRecord.getMedications();

                    return new PersonByStationDTOVar(person.getFirstName(), person.getLastName(), person.getPhone(), person.getAge(), allergies, medications);
                }).toList();


                houseHoldDTOList.add(new HouseHoldDTO(address, personByStationDTOList));
            }

            stationList.put(station, houseHoldDTOList);

        }


        return stationList;
    }
}


