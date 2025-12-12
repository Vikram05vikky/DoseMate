package com.dosemate.Dosemate.service;

import com.dosemate.Dosemate.model.Admin;
import com.dosemate.Dosemate.model.Doctor;
import com.dosemate.Dosemate.model.PatientInfo;
import com.dosemate.Dosemate.repo.DoctorRepository;
import com.dosemate.Dosemate.repo.MedicineReminderRepository;
import com.dosemate.Dosemate.repo.PatientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientInfoRepository patientInfoRepository;
    @Autowired
    private MedicineReminderRepository medicineReminderRepository;

    public Doctor registerDoctor(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public Optional<Doctor> getDoctorById(int id) {
        return doctorRepo.findById(id);
    }

    public Doctor loginDoctor(String doctorEmail, String password) {
        return doctorRepo.findByDoctorEmailAndPassword(doctorEmail, password);
    }

    public boolean changePassword(int doctorId, String oldPassword, String newPassword) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(doctorId);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();

            // Check old password
            if (!doctor.getPassword().equals(oldPassword)) {
                return false; // old password does not match
            }

            // Update password
            doctor.setPassword(newPassword);
            doctorRepo.save(doctor);
            return true;
        }
        return false; // doctor not found
    }

    public boolean approveDoctor(int doctorId) {
        return doctorRepo.findById(doctorId).map(doctor -> {
            doctor.setApproved(true);
            doctorRepo.save(doctor);
            return true;
        }).orElse(false);
    }

    public boolean rejectDoctor(int doctorId) {
        return doctorRepo.findById(doctorId).map(doctor -> {
            doctorRepo.delete(doctor); // ❌ remove doctor from table
            return true;
        }).orElse(false);
    }

    // public Doctor loginDoctor(String doctorName, String password) {
    // return doctorRepo.findByDoctorNameAndPassword(doctorName, password);
    // }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    // ✅ Get count of all doctors
    public long getTotalDoctors() {
        return doctorRepo.count();
    }

    // ✅ (Optional) Get count by approval status
    public long countByApprovalStatus(boolean approved) {
        return doctorRepo.countByApproved(approved);
    }

    // ✅ Count patients treated by a given doctor
    public long getPatientCountByDoctor(int doctorId) {
        return patientInfoRepository.countPatientsByDoctorId(doctorId);
    }

    // ✅ Active patient count
    public long getActivePatientCount(int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Get all patient IDs under this doctor
        List<PatientInfo> allPatients = patientInfoRepository.findByDoctor_DoctorId(doctorId);

        // Get active patient IDs from medicine reminder
        List<Integer> activePatientIds = medicineReminderRepository
                .findActivePatientIdsByDoctorName(doctor.getDoctorName());

        // Count only patients that are active
        return allPatients.stream()
                .filter(p -> activePatientIds.contains(p.getPatientId()))
                .count();
    }

    // ✅ Completed patient count
    public long getCompletedPatientCount(int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Get all patient IDs under this doctor
        List<PatientInfo> allPatients = patientInfoRepository.findByDoctor_DoctorId(doctorId);

        // Get active patient IDs
        List<Integer> activePatientIds = medicineReminderRepository
                .findActivePatientIdsByDoctorName(doctor.getDoctorName());

        // Count only patients that are not active
        return allPatients.stream()
                .filter(p -> !activePatientIds.contains(p.getPatientId()))
                .count();
    }

    public List<PatientInfo> getPatientsByDoctorId(int doctorId) {
        // This uses the method available in PatientInfoRepository
        return patientInfoRepository.findByDoctor_DoctorId(doctorId);
    }

}
