package com.P5.SafetyNet.Daos;

import com.P5.SafetyNet.InterfaceRepository.PersonRepository;
import com.P5.SafetyNet.Models.Data;
import com.P5.SafetyNet.Models.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class PersonDao {

        @Autowired
        private PersonRepository personRecordRepository;

        @Value("${data.json.path}")
        private String dataJsonPath;

        @PostConstruct
        public void loadData() throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            Data data = mapper.readValue(new File(dataJsonPath), Data.class);

            for (Person person : data.getPersons()) {
                personRecordRepository.save(person);
            }
        }


        public Optional<Person> getPerson(final Long id) {
        return personRecordRepository.findById(id);
    }

        public Iterable<Person> getPersons() {
        return personRecordRepository.findAll();
    }

        public void savePerson(Person person) {
        personRecordRepository.save(person);
    }

        public void deletePerson(final Long id) {
        personRecordRepository.deleteById(id);
    }
        }

