package com.dosemate.Dosemate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dosemate.Dosemate.DTO.PatientVisitDTO;
import com.dosemate.Dosemate.model.MedicineReminder;
import com.dosemate.Dosemate.model.PatientInfo;
import com.dosemate.Dosemate.repo.MedicineReminderRepository;
import com.dosemate.Dosemate.repo.PatientInfoRepository;
import com.dosemate.Dosemate.service.PatientService;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
private MedicineReminderRepository medicineReminderRepo;

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

    // @GetMapping("/{patientId}/prescriptions")
    // public PatientVisitDTO getPatientHistory(@PathVariable int patientId) {
    //     return patientService.getPatientVisitHistory(patientId);
    // }

    @GetMapping("/reminders/{patientId}")
public List<MedicineReminder> getReminders(@PathVariable int patientId) {
    return medicineReminderRepo.findByPatientId(patientId);
}

}
