package com.P5.SafetyNet.InterfaceRepository;



import com.P5.SafetyNet.Models.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long > {

}
