package com.dosemate.Dosemate.controller;

import com.dosemate.Dosemate.DTO.ChangePasswordRequest;
import com.dosemate.Dosemate.model.Admin;
import com.dosemate.Dosemate.model.Doctor;
import com.dosemate.Dosemate.model.PatientInfo;
import com.dosemate.Dosemate.service.DoctorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<Doctor> registerDoctor(@RequestBody Doctor doctor) {
        doctor.setApproved(false);
        Doctor saved = doctorService.registerDoctor(doctor);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable int id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable int id,
            @RequestBody ChangePasswordRequest request) {

        boolean success = doctorService.changePassword(id, request.getOldPassword(), request.getNewPassword());

        if (success) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid old password or doctor not found.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginDoctor(@RequestBody Doctor doctor) {
        Doctor loggedInDoctor = doctorService.loginDoctor(doctor.getDoctorEmail(), doctor.getPassword());

        if (loggedInDoctor != null && loggedInDoctor.isApproved()) {
            return ResponseEntity.ok(loggedInDoctor); // ✅ Return full doctor details
        } else if (loggedInDoctor != null && !loggedInDoctor.isApproved()) {
            return ResponseEntity.badRequest().body("Doctor account not approved yet!");
        } else {
            return ResponseEntity.badRequest().body("Invalid Email or Password!");
        }
    }

    // ✅ Get total number of patients treated by a specific doctor
    @GetMapping("/{doctorId}/patientCount")
    public ResponseEntity<?> getPatientCountByDoctor(@PathVariable int doctorId) {
        long count = doctorService.getPatientCountByDoctor(doctorId);
        return ResponseEntity.ok(count);
    }

    // ✅ Get Active Patient Count only
    @GetMapping("/{doctorId}/activePatientCount")
    public ResponseEntity<?> getActivePatientCount(@PathVariable int doctorId) {
        try {
            long count = doctorService.getActivePatientCount(doctorId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Get Completed Patient Count only
    @GetMapping("/{doctorId}/completedPatientCount")
    public ResponseEntity<?> getCompletedPatientCount(@PathVariable int doctorId) {
        try {
            long count = doctorService.getCompletedPatientCount(doctorId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Reject Doctor by ID
    @DeleteMapping("/reject/{doctorId}")
    public ResponseEntity<String> rejectDoctor(@PathVariable int doctorId) {
        boolean deleted = doctorService.rejectDoctor(doctorId);
        if (deleted) {
            return ResponseEntity.ok("Doctor successfully rejected");
        } else {
            return ResponseEntity.badRequest().body("Doctor not found");
        }
    }

    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<PatientInfo>> getPatientsByDoctorId(@PathVariable int doctorId) {
        // The doctorService (which we assume has access to PatientInfoRepository)
        // will fetch the list of patients under this doctorId.
        List<PatientInfo> patients = doctorService.getPatientsByDoctorId(doctorId);

        return ResponseEntity.ok(patients);
    }

}
