package com.P5.SafetyNet.InterfaceRepository;

import com.P5.SafetyNet.Models.Firestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirestationRepository extends JpaRepository<Firestation, Long> {

    List<Firestation> findByAddress(String address);


    List<Firestation> findByStation(String station);
}