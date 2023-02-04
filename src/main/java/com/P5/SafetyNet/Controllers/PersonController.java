package com.P5.SafetyNet.Controllers;


import com.P5.SafetyNet.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public Iterable<com.P5.SafetyNet.Models.Person> getPersons() {

        return personService.getPersons();
    }

    @GetMapping("/person/{id}")
    public Optional<com.P5.SafetyNet.Models.Person> getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    @PostMapping("/person")
    public com.P5.SafetyNet.Models.Person savePerson(@RequestBody com.P5.SafetyNet.Models.Person person) {
        return personService.savePerson(person);
    }

    @PutMapping("/person/{id}")
    public com.P5.SafetyNet.Models.Person updatePerson(@PathVariable Long id, @RequestBody com.P5.SafetyNet.Models.Person person) {
        return personService.updatePerson(id, person);
    }

    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }
}