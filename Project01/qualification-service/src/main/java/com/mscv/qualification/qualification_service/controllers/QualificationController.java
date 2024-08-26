package com.mscv.qualification.qualification_service.controllers;

import com.mscv.qualification.qualification_service.entities.Qualification;
import com.mscv.qualification.qualification_service.services.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qualifications")
public class QualificationController {
    @Autowired
    private QualificationService qualificationService;

    @PostMapping
    public ResponseEntity<Qualification> saveQualification(@RequestBody Qualification qualification){
        return ResponseEntity.status(HttpStatus.CREATED).body(qualificationService.create(qualification));
    }

    @GetMapping
    public ResponseEntity<List<Qualification>> qualificationsList(){
        return ResponseEntity.ok(qualificationService.getAllQualifications());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Qualification>> qualificationsListByUserId(@PathVariable String userId){
        return ResponseEntity.ok(qualificationService.getQualificationByUserId(userId));
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Qualification>> qualificationsListByHotelId(@PathVariable String hotelId){
        return ResponseEntity.ok(qualificationService.getQualificationByHotelId(hotelId));
    }
}
