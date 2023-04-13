package com.P5.SafetyNet.Controllers;


import com.P5.SafetyNet.Dtos.*;
import com.P5.SafetyNet.Models.Firestation;
import com.P5.SafetyNet.Models.Person;
import com.P5.SafetyNet.Services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public List<com.P5.SafetyNet.Models.Person> getPersons() {
        log.info("GET request received for all persons");
        return personService.getPersons();
    }

    @GetMapping("/persons/{id}")
    public Optional<com.P5.SafetyNet.Models.Person> getPerson(@PathVariable Long id) {
        log.info("GET request received for person with id: " + id);
        return personService.getPerson(id);
    }

    @PostMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> savePerson(@RequestBody com.P5.SafetyNet.Models.Person person) {
        log.info("POST request received to create person: " + person.getFirstName() + " " + person.getLastName());
        Person savedPerson = personService.savePerson(person);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(savedPerson, headers, HttpStatus.CREATED);

    }

    @PutMapping(value = "/persons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody com.P5.SafetyNet.Models.Person person) {
        try {
            log.info("PUT request received to update person with id: " + id);
            Person updatedPerson = personService.updatePerson(id, person);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(updatedPerson, headers, HttpStatus.CREATED);
        } catch (Exception exception) {
            if (exception.getMessage() == "lol tu veux changer ton nom ?") {
                log.error("Bad Request received while trying to update person with id: " + id);
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            throw exception;

        }
    }

    @DeleteMapping("/persons/{id}")
    public void deletePerson(@PathVariable Long id) {
        log.info("DELETE request received for person with id: " + id);
        personService.deletePerson(id);
    }


    @GetMapping("/childAlert")
    public ResponseEntity<List<ChildAlertDTO>> getChildAlert(@RequestParam("address") String address) {
        log.info("GET request received for child alert with address: " + address);
        List<ChildAlertDTO> childListByAddress = personService.getChildByAddress(address);
        if (childListByAddress.isEmpty()) {
            return ResponseEntity.ok().body(Collections.EMPTY_LIST);
        } else {
            return new ResponseEntity(childListByAddress, HttpStatus.OK);
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<EmailCommunityDTO> getEmailByCity(@RequestParam("city") String city) {
        log.info("GET request received for community email with city: " + city);
        EmailCommunityDTO listOfEmails = personService.getEmailByCity(city);

        return new ResponseEntity(listOfEmails, HttpStatus.OK);
    }

    @GetMapping("/fire")
    public ResponseEntity<PersonByAddressDTO> getPersonsByAddress(@RequestParam("address") String address) {
        log.info("GET request received for persons by address: " + address);
        PersonByAddressDTO listOfPersonsByAddress = personService.returnListByAddress(address);
        return new ResponseEntity(listOfPersonsByAddress, HttpStatus.OK);

    }


    @GetMapping("/phoneAlert")
    public ResponseEntity<TelephoneByAddressDTO> getPhoneByAddress(@RequestParam("firestation") Firestation firestation) {
        log.info("Received request to retrieve phone numbers for fire station: {}", firestation);
        TelephoneByAddressDTO listOfPhonesDTO = personService.getPhoneByStation(firestation);
        log.info("Returning list of phone numbers for fire station: {}", firestation);
        return new ResponseEntity(listOfPhonesDTO, HttpStatus.OK);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<PersonByNameDTO> getPersonByFirstNameAndLastName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        log.info("Received request to retrieve person information for first name: {} and last name: {}", firstName, lastName);
        PersonByNameDTO pByFirstNameAndLastName = personService.getPersonByFirstNameAndLastName(firstName, lastName);
        log.info("Returning person information for first name: {} and last name: {}", firstName, lastName);
        return new ResponseEntity(pByFirstNameAndLastName, HttpStatus.OK);
    }







    /*quand on request le param "address", retourne la liste de type ChildAlertDTO.
    la classe Data Acces Object doit comprendre
    le prénom et le nom de famille de chaque enfant, son âge et une liste des autres membres de la maison-->
    voir classe ChildAlertDTO

    */

}

