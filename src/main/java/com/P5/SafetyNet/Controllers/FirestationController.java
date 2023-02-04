package com.P5.SafetyNet.Controllers;


import com.P5.SafetyNet.Services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestations")
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping("/firestations/{id}")
    public com.P5.SafetyNet.Models.Firestation getFirestation(@PathVariable Long id){
        return firestationService.getFirestation(id).orElse(null);
    }


    @GetMapping("/firestations")
    public Iterable<com.P5.SafetyNet.Models.Firestation> getFirestations(){
        return firestationService.getFirestations();
    }


    @DeleteMapping("/firestations")
    public void deleteFirestation(@PathVariable Long id){
        firestationService.deleteFirestation(id);
    }

    @PostMapping("/firestations")
    public com.P5.SafetyNet.Models.Firestation saveFirestation(@RequestBody com.P5.SafetyNet.Models.Firestation firestation){
        return firestationService.saveFirestation(firestation);
    }
}
