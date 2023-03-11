package com.P5.SafetyNet.Services;


import com.P5.SafetyNet.Dtos.ChildAlertDTO;
import com.P5.SafetyNet.InterfaceRepository.PersonRepository;
import com.P5.SafetyNet.Models.Person;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class PersonService {

    private PersonRepository personRepository;
    private String address;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> getPerson(@PathVariable Long id) {
        return personRepository.findById(id);
    }

    public Iterable<Person> getPersons(){
        return personRepository.findAll();
    }

    public void deletePerson(final Long id) {
        personRepository.deleteById(id);
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
    public Person updatePerson(Long id, Person newPersonData) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person personToUpdate = optionalPerson.get();
            if (!personToUpdate.getFirstName().equals(newPersonData.getFirstName()) ||
                    !personToUpdate.getLastName().equals(newPersonData.getLastName())) {
                throw new IllegalArgumentException("lol tu veux changer ton nom ?");
            }

            personToUpdate.setAddress(newPersonData.getAddress());
            personToUpdate.setCity(newPersonData.getCity());
            personToUpdate.setZip(newPersonData.getZip());
            personToUpdate.setPhone(newPersonData.getPhone());
            personToUpdate.setEmail(newPersonData.getEmail());
            return personRepository.save(personToUpdate);
        } else {
            throw new IllegalArgumentException("Person with id " + id + " not found");

        }
    }
        public List<Person> getPersonByAddress(String address) {
            return personRepository.findByAddress(address);
        }

    public List<ChildAlertDTO> getChildByAddress(String address) {
        List<ChildAlertDTO> childrenList = getPersonByAddress(address).stream()
                .filter(person -> person.getAge() <= 18)
                .map(child -> {
                    List<Person> otherOccupants;
                    otherOccupants = getPersonByAddress(address).stream()
                            .filter(p -> !p.getId().equals(child.getId()))
                            .toList();
                    return new ChildAlertDTO(child.getFirstName(), child.getLastName(), child.getAge(),
                            otherOccupants.stream()
                                    .map(otherOccupant -> otherOccupant.getFirstName() + " " + otherOccupant.getLastName())
                                    .toList());
                })
                .toList();
        return childrenList;
    }


}
