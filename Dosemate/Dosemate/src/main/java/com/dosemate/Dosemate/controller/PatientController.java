package com.dosemate.Dosemate.controller;

import com.dosemate.Dosemate.DTO.PatientVisitDTO;
import com.dosemate.Dosemate.model.PatientInfo;
import com.dosemate.Dosemate.repo.PatientInfoRepository;
import com.dosemate.Dosemate.service.PatientService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientInfoRepository repo;

    @PostMapping("/new/{doctorId}")
    public ResponseEntity<PatientInfo> saveNewPatient(@RequestBody PatientInfo patient, @PathVariable int doctorId) {
        PatientInfo savedPatient = patientService.saveNewPatient(patient, doctorId);
        return ResponseEntity.ok(savedPatient);
    }

    @GetMapping("/search")
    public ResponseEntity<PatientInfo> getPatientByName(@RequestParam String name) {
        return patientService.getPatientByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/visit/{doctorId}")
    public ResponseEntity<PatientInfo> addVisitToExistingPatient(@RequestBody PatientVisitDTO visitDTO,
            @PathVariable int doctorId) {
        PatientInfo updatedPatient = patientService.addVisitToExistingPatient(visitDTO, doctorId);
        return ResponseEntity.ok(updatedPatient);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientInfo>> getAllPatients() {
        List<PatientInfo> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    // ✅ Missing Endpoint: Fetch patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientInfo> getPatientById(@PathVariable int id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
