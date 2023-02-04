package com.P5.SafetyNet.InterfaceRepository;

import com.P5.SafetyNet.Models.Firestation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirestationRepository extends JpaRepository<Firestation, Long> {
}