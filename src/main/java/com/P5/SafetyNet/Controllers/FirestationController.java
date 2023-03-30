package com.P5.SafetyNet.Controllers;


import com.P5.SafetyNet.Dtos.HouseHoldDTO;
import com.P5.SafetyNet.Dtos.PersonByStationDTO;
import com.P5.SafetyNet.Dtos.PersonByStationDTOList;
import com.P5.SafetyNet.Models.Firestation;
import com.P5.SafetyNet.Services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping("/firestations/{id}")
    public com.P5.SafetyNet.Models.Firestation getFirestation(@PathVariable Long id) {
        return firestationService.getFirestation(id).orElse(null);
    }


    @GetMapping("/firestations")
    public Iterable<com.P5.SafetyNet.Models.Firestation> getFirestations() {
        return firestationService.getFirestations();
    }


    @DeleteMapping("/firestations/{id}")
    public void deleteFirestation(@PathVariable Long id) {
        firestationService.deleteFirestation(id);
    }

    @PostMapping(value = "/firestations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Firestation> saveFirestation(@RequestBody com.P5.SafetyNet.Models.Firestation firestation) {
        Firestation savedFirestation = firestationService.saveFirestation(firestation);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(savedFirestation, headers, HttpStatus.CREATED);
    }

    @PutMapping("/firestations/{id}")
    public ResponseEntity<Firestation> updatePerson(@PathVariable Long id, @RequestBody Firestation firestation) {
        Firestation updatedFirestation = firestationService.updateFirestation(id, firestation);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(updatedFirestation, headers, HttpStatus.CREATED);
    }

    @GetMapping("/firestation")
    public ResponseEntity<List<PersonByStationDTO>> getPersonsByStation(@RequestParam("stationNumber") Long stationNumber) {
        PersonByStationDTOList personByStation = firestationService.returnPersonsByFireStation(stationNumber);

        return new ResponseEntity(personByStation, HttpStatus.OK);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<HashMap<Long, List<HouseHoldDTO>>> getHouseHoldsByStation(@RequestParam("stations") List<Long> stations) {
        System.out.println(stations);
        try {
            HashMap<Long, List<HouseHoldDTO>> houseHoldDTOList = firestationService.getHouseHoldsByStation(stations);
            if (houseHoldDTOList.isEmpty()) {
                System.out.println("etape1");
                return ResponseEntity.noContent().build();
            } else {
                System.out.println("etape2");
                return ResponseEntity.ok(houseHoldDTOList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }

    }
//va retourner un DTO avec comme parametres un string et une liste de Person

}
