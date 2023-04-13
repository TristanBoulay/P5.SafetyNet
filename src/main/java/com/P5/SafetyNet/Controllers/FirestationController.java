package com.P5.SafetyNet.Controllers;


import com.P5.SafetyNet.Dtos.HouseHoldDTO;
import com.P5.SafetyNet.Dtos.PersonByStationDTO;
import com.P5.SafetyNet.Dtos.PersonByStationDTOList;
import com.P5.SafetyNet.Models.Firestation;
import com.P5.SafetyNet.Services.FirestationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping("/firestations/{id}")
    public Firestation getFirestation(@PathVariable Long id) {
        log.info("GET request received for firestation with ID: {}", id);
        return firestationService.getFirestation(id).orElse(null);
    }

    @GetMapping("/firestations")
    public Iterable<Firestation> getFirestations() {
        log.info("GET request received for all firestations");
        return firestationService.getFirestations();
    }

    @DeleteMapping("/firestations/{id}")
    public void deleteFirestation(@PathVariable Long id) {
        log.info("DELETE request received for firestation with ID: {}", id);
        firestationService.deleteFirestation(id);
    }

    @PostMapping(value = "/firestations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Firestation> saveFirestation(@RequestBody Firestation firestation) {
        log.info("POST request received to save firestation: {}", firestation);
        Firestation savedFirestation = firestationService.saveFirestation(firestation);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(savedFirestation, headers, HttpStatus.CREATED);
    }

    @PutMapping("/firestations/{id}")
    public ResponseEntity<Firestation> updatePerson(@PathVariable Long id, @RequestBody Firestation firestation) {
        log.info("PUT request received to update firestation with ID: {}", id);
        Firestation updatedFirestation = firestationService.updateFirestation(id, firestation);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(updatedFirestation, headers, HttpStatus.CREATED);
    }

    @GetMapping("/firestation")
    public ResponseEntity<List<PersonByStationDTO>> getPersonsByStation(@RequestParam("stationNumber") Long stationNumber) {
        log.info("GET request received for persons by station with stationNumber: {}", stationNumber);
        PersonByStationDTOList personByStation = firestationService.returnPersonsByFireStation(stationNumber);

        return new ResponseEntity(personByStation, HttpStatus.OK);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<HashMap<Long, List<HouseHoldDTO>>> getHouseHoldsByStation(@RequestParam("stations") List<Long> stations) {
        log.info("GET request received for households by stations: {}", stations);
        try {
            HashMap<Long, List<HouseHoldDTO>> houseHoldDTOList = firestationService.getHouseHoldsByStation(stations);
            if (houseHoldDTOList.isEmpty()) {
                log.error("No households found for stations: {}", stations);
                return ResponseEntity.noContent().build();
            } else {
                log.info("Found households for stations: {}", stations);
                return ResponseEntity.ok(houseHoldDTOList);
            }
        } catch (Exception e) {
            log.error("An error occurred while getting households for stations: {}", stations, e);
            return null;
        }
    }
}






