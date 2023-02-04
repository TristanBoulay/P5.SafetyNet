package com.P5.SafetyNet.InterfaceRepository;

import com.P5.SafetyNet.Models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}