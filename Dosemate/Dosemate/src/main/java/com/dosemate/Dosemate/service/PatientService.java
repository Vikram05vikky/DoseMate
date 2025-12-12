package com.dosemate.Dosemate.service;

import com.dosemate.Dosemate.DTO.PatientVisitDTO;
import com.dosemate.Dosemate.model.*;
import com.dosemate.Dosemate.repo.DoctorRepository;
import com.dosemate.Dosemate.repo.MedicineReminderRepository;
import com.dosemate.Dosemate.repo.OtherReminderRepository;
import com.dosemate.Dosemate.repo.PatientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientInfoRepository patientRepo;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private MedicineReminderRepository medicineReminderRepo;
    @Autowired
    private OtherReminderRepository otherReminderRepo;
    @Autowired
    private SmsSender smsSender;

    public PatientInfo saveNewPatient(PatientInfo patient, int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        if (!doctor.isApproved()) {
            throw new RuntimeException("Doctor is not approved by admin. Cannot add patients.");
        }
        patient.setDoctor(doctor);

        // Save patient and cascading details
        PatientInfo saved = patientRepo.save(patient);

        // Save medicine reminders
        for (Medicine med : saved.getMedicines()) {
            MedicineReminder reminder = new MedicineReminder();
            reminder.setPatientId(saved.getPatientId());
            reminder.setPatientName(saved.getName());
            reminder.setDoctorName(doctor.getDoctorName());
            reminder.setMedicineName(med.getMedicineName());
            reminder.setSession(med.getSession());
            reminder.setTakingmethod(med.getTakingmethod());
            reminder.setStartDate(med.getStartDate());
            reminder.setLastDate(med.getLastDate());
            reminder.setTotalQuantity(med.getTotalQuantity());
            reminder.setQuantityPerSession(med.getQuantityPerSession());
            medicineReminderRepo.save(reminder);
        }

        // Save other treatment reminders
        for (OtherTreatment treatment : saved.getOtherTreatments()) {
            OtherReminder reminder = new OtherReminder();
            reminder.setPatientId(saved.getPatientId());
            reminder.setPatientName(saved.getName());
            reminder.setDoctorName(doctor.getDoctorName());
            reminder.setTreatmentName(treatment.getTreatmentName());
            reminder.setDateOfReminder(treatment.getDateOfReminder());
            reminder.setSession(treatment.getSession());
            otherReminderRepo.save(reminder);
        }
        System.out.println("SNS Going To Work");
        String smsMessage = buildMedicineSms(saved, doctor, saved.getMedicines(), saved.getOtherTreatments(), true);
        // smsSender.sendSms(saved.getPhoneNo(), smsMessage);
        try {
            System.out.println("📨 Attempting to send SMS...");
            smsSender.sendSms(saved.getPhoneNo(), smsMessage); // for saveNewPatient
            System.out.println("✅ SMS sent successfully!");
        } catch (Exception e) {
            System.err.println("❌ Failed to send SMS via SNS: " + e.getMessage());
            e.printStackTrace(); // This will print the full stack trace to console
        }
        return saved;
    }

    public Optional<PatientInfo> getPatientByName(String name) {
        return patientRepo.findByName(name);
    }

    public PatientInfo addVisitToExistingPatient(PatientVisitDTO dto, int doctorId) {

        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (!doctor.isApproved()) {
            throw new RuntimeException("Doctor is not approved by admin. Cannot add patients.");
        }

        PatientInfo patient = patientRepo.findByName(dto.getPatientName())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setDoctor(doctor);

        // Append new DoctorExaminations
        if (dto.getDoctorExaminations() != null) {
            patient.getDoctorExaminations().addAll(dto.getDoctorExaminations());
        }

        // Append new Medicines and create reminders
        if (dto.getMedicines() != null) {
            for (Medicine med : dto.getMedicines()) {
                patient.getMedicines().add(med);

                MedicineReminder reminder = new MedicineReminder();
                reminder.setPatientId(patient.getPatientId());
                reminder.setPatientName(patient.getName());
                reminder.setDoctorName(patient.getDoctor().getDoctorName());
                reminder.setMedicineName(med.getMedicineName());
                reminder.setSession(med.getSession());
                reminder.setTakingmethod(med.getTakingmethod());
                reminder.setStartDate(med.getStartDate());
                reminder.setLastDate(med.getLastDate());
                reminder.setTotalQuantity(med.getTotalQuantity());
                reminder.setQuantityPerSession(med.getQuantityPerSession());
                medicineReminderRepo.save(reminder);
            }
        }

        // Append new OtherTreatments and create reminders
        if (dto.getOtherTreatments() != null) {
            for (OtherTreatment treatment : dto.getOtherTreatments()) {
                patient.getOtherTreatments().add(treatment);

                OtherReminder reminder = new OtherReminder();
                reminder.setPatientId(patient.getPatientId());
                reminder.setPatientName(patient.getName());
                reminder.setDoctorName(patient.getDoctor().getDoctorName());
                reminder.setTreatmentName(treatment.getTreatmentName());
                reminder.setDateOfReminder(treatment.getDateOfReminder());
                reminder.setSession(treatment.getSession());
                otherReminderRepo.save(reminder);
            }
        }
        String smsMessage = buildMedicineSms(patient, patient.getDoctor(), dto.getMedicines(), dto.getOtherTreatments(),
                false);
        smsSender.sendSms(patient.getPhoneNo(), smsMessage);

        return patientRepo.save(patient);
    }

    // private String buildMedicineSms(PatientInfo patient, Doctor doctor,
    // List<Medicine> medicines, boolean isNewPatient) {
    // StringBuilder sb = new StringBuilder();
    //
    // if (isNewPatient) {
    // sb.append("Hello ").append(patient.getName()).append(", this is Dr.
    // ").append(doctor.getDoctorName()).append(".\n");
    // sb.append("You have been registered successfully.\n");
    // } else {
    // sb.append("Hello ").append(patient.getName()).append(", this is Dr.
    // ").append(doctor.getDoctorName()).append(".\n");
    // sb.append("Here are your new prescribed medicines:\n");
    // }
    //
    // sb.append("\nName | Session | Qty/Session | Total\n");
    //
    // for (Medicine med : medicines) {
    // sb.append(med.getMedicineName()).append(" | ")
    // .append(med.getSession()).append(" | ")
    // .append(med.getQuantityPerSession()).append(" | ")
    // .append(med.getTotalQuantity()).append("\n");
    // }
    //
    // return sb.toString();
    // }

    private final PatientInfoRepository patientInfoRepository;

    public PatientService(PatientInfoRepository patientInfoRepository) {
        this.patientInfoRepository = patientInfoRepository;
    }

    public List<PatientInfo> getAllPatients() {
        return patientInfoRepository.findAll();
    }

    public Optional<PatientInfo> getPatientById(int id) {
        return patientInfoRepository.findById(id);
    }

    private String buildMedicineSms(PatientInfo patient, Doctor doctor, List<Medicine> medicines,
            List<OtherTreatment> treatments, boolean isNewPatient) {
        StringBuilder sb = new StringBuilder();

        if (isNewPatient) {
            sb.append("Hello ").append(patient.getName()).append(", this is ")
                    .append(doctor.getDoctorName()).append(".\n");
            sb.append("You have been visited us to the hospital.\n");
        } else {
            sb.append("Hello ").append(patient.getName()).append(", this is Dr. ")
                    .append(doctor.getDoctorName()).append(".\n");
            sb.append("Here are your new prescribed medicines and treatments:\n");
        }

        // Medicine Details
        sb.append("\n📋 Medicines:\n");
        sb.append("Name | Session | Taking Method | Qty Per Session| Total\n");
        for (Medicine med : medicines) {
            sb.append(med.getMedicineName()).append(" | ")
                    .append(med.getSession()).append(" | ")
                    .append(med.getTakingmethod()).append(" | ")
                    .append(med.getQuantityPerSession()).append(" | ")
                    .append(med.getTotalQuantity()).append("\n");
        }

        // Treatment Details
        if (treatments != null && !treatments.isEmpty()) {
            sb.append("\n🩺 Treatments:\n");
            sb.append("Name | Session | Date\n");
            for (OtherTreatment treatment : treatments) {
                sb.append(treatment.getTreatmentName()).append(" | ")
                        .append(treatment.getSession()).append(" | ")
                        .append(treatment.getDateOfReminder()).append("\n");
            }
        }

        return sb.toString();
    }

    public long getTotalPatients() {
        return patientRepo.count();
    }

}
