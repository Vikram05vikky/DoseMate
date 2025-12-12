package com.dosemate.Dosemate.controller;

import com.dosemate.Dosemate.model.Admin;
import com.dosemate.Dosemate.model.PatientInfo;
import com.dosemate.Dosemate.service.AdminService;
import com.dosemate.Dosemate.service.DoctorService;
import com.dosemate.Dosemate.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        Admin saved = adminService.registerAdmin(admin);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable int id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ New Endpoint: Approve Doctor
    @PutMapping("/approveDoctor/{doctorId}")
    public ResponseEntity<String> approveDoctor(@PathVariable int doctorId) {
        boolean approved = doctorService.approveDoctor(doctorId);
        if (approved) {
            return ResponseEntity.ok("Doctor approved successfully");
        } else {
            return ResponseEntity.badRequest().body("Doctor not found");
        }
    }

    // ✅ Optional: Reject Doctor (delete or keep with rejected flag)
    @DeleteMapping("/rejectDoctor/{doctorId}")
    public ResponseEntity<String> rejectDoctor(@PathVariable int doctorId) {
        boolean deleted = doctorService.rejectDoctor(doctorId);
        if (deleted) {
            return ResponseEntity.ok("Doctor rejected and removed from the database");
        } else {
            return ResponseEntity.badRequest().body("Doctor not found");
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody Admin request) {
        boolean isAuthenticated = adminService.login(request.getAdminName(), request.getPassword());

        if (isAuthenticated) {
            return "Login Successful!";
        } else {
            return "Invalid Admin Name or Password!";
        }
    }

    // ✅ Get all doctors (approved + not approved)
    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    // ✅ Get total doctor count
    @GetMapping("/doctorCount")
    public ResponseEntity<?> getDoctorCount() {
        long totalDoctors = doctorService.getTotalDoctors();
        return ResponseEntity.ok(totalDoctors);
    }

    // ✅ Get count of approved doctors
    @GetMapping("/doctorCount/approved")
    public ResponseEntity<?> getApprovedDoctorCount() {
        long approvedCount = doctorService.countByApprovalStatus(true);
        return ResponseEntity.ok(approvedCount);
    }

    // ✅ Get count of unapproved doctors
    @GetMapping("/doctorCount/pending")
    public ResponseEntity<?> getPendingDoctorCount() {
        long pendingCount = doctorService.countByApprovalStatus(false);
        return ResponseEntity.ok(pendingCount);
    }

    @GetMapping("/patientCount")
    public ResponseEntity<?> getPatientCount() {
        long totalPatients = patientService.getTotalPatients();
        return ResponseEntity.ok(totalPatients);
    }

    // ✅ Active patient count
    @GetMapping("/activePatientCount")
    public ResponseEntity<?> getActivePatientCount() {
        try {
            long count = adminService.getActivePatientCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching active patient count");
        }
    }

    // ✅ Completed patient count
    @GetMapping("/completedPatientCount")
    public ResponseEntity<?> getCompletedPatientCount() {
        try {
            long count = adminService.getCompletedPatientCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching completed patient count");
        }
    }

}
