package com.P5.SafetyNet.Services;


import com.P5.SafetyNet.InterfaceRepository.PersonRepository;
import com.P5.SafetyNet.Models.Person;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Data
@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;



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
            personToUpdate.setFirstName(newPersonData.getFirstName());
            personToUpdate.setLastName(newPersonData.getLastName());
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
}
