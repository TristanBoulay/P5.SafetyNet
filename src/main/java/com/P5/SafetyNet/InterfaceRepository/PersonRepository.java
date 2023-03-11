package com.P5.SafetyNet.InterfaceRepository;

import com.P5.SafetyNet.Models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByAddress(String address);


}