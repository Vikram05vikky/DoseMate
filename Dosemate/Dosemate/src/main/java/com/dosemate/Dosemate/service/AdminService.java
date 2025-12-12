package com.dosemate.Dosemate.service;

import com.dosemate.Dosemate.model.Admin;
import com.dosemate.Dosemate.model.PatientInfo;
import com.dosemate.Dosemate.repo.AdminRepository;
import com.dosemate.Dosemate.repo.MedicineReminderRepository;
import com.dosemate.Dosemate.repo.PatientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService
{
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private MedicineReminderRepository medicineReminderRepository;
    @Autowired
    private PatientInfoRepository patientInfoRepository;

    public Admin registerAdmin(Admin doctor)
    {
        return adminRepo.save(doctor);
    }

    public Optional<Admin> getAdminById(int id)
    {
        return adminRepo.findById(id);
    }

    public boolean login(String adminName, String password) {
        Optional<Admin> adminOpt = adminRepo.findByAdminName(adminName);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return admin.getPassword().equals(password); // plain comparison
        }
        return false;
    }

    // ✅ Count of active patients
    public long getActivePatientCount() {
        // Count distinct patient IDs in MedicineReminder
        return medicineReminderRepository.countDistinctPatients();
    }

    // ✅ Count of completed patients
    public long getCompletedPatientCount() {
        long totalPatients = patientInfoRepository.count(); // Total patients
        long activePatients = medicineReminderRepository.countDistinctPatients(); // Active treatments
        return Math.max(totalPatients - activePatients, 0); // Completed = total - active
    }
}
