package com.P5.SafetyNet.InterfaceRepository;



import com.P5.SafetyNet.Models.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long > {
    @Query("select u from MedicalRecord u where u.firstName = ?1 and u.lastName = ?2")
    MedicalRecord searchByFirstAndLastName(String firstName, String lastName);
}
