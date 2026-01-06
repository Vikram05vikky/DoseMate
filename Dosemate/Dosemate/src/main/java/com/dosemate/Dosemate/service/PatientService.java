package com.dosemate.Dosemate.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dosemate.Dosemate.DTO.PatientVisitDTO;
import com.dosemate.Dosemate.model.Doctor;
import com.dosemate.Dosemate.model.Medicine;
import com.dosemate.Dosemate.model.MedicineReminder;
import com.dosemate.Dosemate.model.OtherReminder;
import com.dosemate.Dosemate.model.OtherTreatment;
import com.dosemate.Dosemate.model.PatientHistory;
import com.dosemate.Dosemate.model.PatientInfo;
import com.dosemate.Dosemate.repo.DoctorRepository;
import com.dosemate.Dosemate.repo.MedicineReminderRepository;
import com.dosemate.Dosemate.repo.OtherReminderRepository;
import com.dosemate.Dosemate.repo.PatientHistoryRepository;
import com.dosemate.Dosemate.repo.PatientInfoRepository;


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
    @Autowired
    private IndicTransService indicTransService;
    @Autowired
private PatientHistoryRepository patientHistoryRepository;
    
    @Autowired
    private OlamaService olamaService;

    // public PatientInfo saveNewPatient(PatientInfo patient, int doctorId) {
    //     Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
    //     if (!doctor.isApproved()) {
    //         throw new RuntimeException("Doctor is not approved by admin. Cannot add patients.");
    //     }
    //     patient.setDoctor(doctor);

    //     // Save patient and cascading details
    //     PatientInfo saved = patientRepo.save(patient);

    //     // Save medicine reminders
    //     for (Medicine med : saved.getMedicines()) {
    //         MedicineReminder reminder = new MedicineReminder();
    //         reminder.setPatientId(saved.getPatientId());
    //         reminder.setPatientName(saved.getName());
    //         reminder.setDoctorName(doctor.getDoctorName());
    //         reminder.setMedicineName(med.getMedicineName());
    //         reminder.setSession(med.getSession());
    //         reminder.setTakingmethod(med.getTakingmethod());
    //         reminder.setStartDate(med.getStartDate());
    //         reminder.setLastDate(med.getLastDate());
    //         reminder.setTotalQuantity(med.getTotalQuantity());
    //         reminder.setQuantityPerSession(med.getQuantityPerSession());
    //         medicineReminderRepo.save(reminder);
    //     }

    //     // Save other treatment reminders
    //     for (OtherTreatment treatment : saved.getOtherTreatments()) {
    //         OtherReminder reminder = new OtherReminder();
    //         reminder.setPatientId(saved.getPatientId());
    //         reminder.setPatientName(saved.getName());
    //         reminder.setDoctorName(doctor.getDoctorName());
    //         reminder.setTreatmentName(treatment.getTreatmentName());
    //         reminder.setDateOfReminder(treatment.getDateOfReminder());
    //         reminder.setSession(treatment.getSession());
    //         otherReminderRepo.save(reminder);
    //     }
    //     System.out.println("SNS Going To Work");
    //     String smsMessage = buildMedicineSms(saved, doctor, saved.getMedicines(), saved.getOtherTreatments(), true);
    //     // smsSender.sendSms(saved.getPhoneNo(), smsMessage);
    //     try {
    //         System.out.println("📨 Attempting to send SMS...");
    //         smsSender.sendSms(saved.getPhoneNo(), smsMessage); // for saveNewPatient
    //         System.out.println("✅ SMS sent successfully!");
    //     } catch (Exception e) {
    //         System.err.println("❌ Failed to send SMS via SNS: " + e.getMessage());
    //         e.printStackTrace(); // This will print the full stack trace to console
    //     }
    //     return saved;
    // }

    // public PatientInfo saveNewPatient(PatientInfo patient, int doctorId)
    // {
    //     Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
    //     if (!doctor.isApproved()) {
    //         throw new RuntimeException("Doctor is not approved by admin. Cannot add patients.");
    //     }
    //     patient.setDoctor(doctor);

    //     // Save patient and cascading details
    //     PatientInfo saved = patientRepo.save(patient);

        

    //     // Save medicine reminders
    //     for (Medicine med : saved.getMedicines()) {
    //         MedicineReminder reminder = new MedicineReminder();
    //         reminder.setPatientId(saved.getPatientId());
    //         reminder.setPatientName(saved.getName());
    //         reminder.setDoctorName(doctor.getDoctorName());
    //         reminder.setMedicineName(med.getMedicineName());
    //         reminder.setSession(med.getSession());
    //         reminder.setTakingmethod(med.getTakingmethod());
    //         reminder.setStartDate(med.getStartDate());
    //         reminder.setLastDate(med.getLastDate());
    //         reminder.setTotalQuantity(med.getTotalQuantity());
    //         reminder.setQuantityPerSession(med.getQuantityPerSession());
    //         medicineReminderRepo.save(reminder);
    //     }

    //     // Save other treatment reminders
    //     for (OtherTreatment treatment : saved.getOtherTreatments()) {
    //         OtherReminder reminder = new OtherReminder();
    //         reminder.setPatientId(saved.getPatientId());
    //         reminder.setPatientName(saved.getName());
    //         reminder.setDoctorName(doctor.getDoctorName());
    //         reminder.setTreatmentName(treatment.getTreatmentName());
    //         reminder.setDateOfReminder(treatment.getDateOfReminder());
    //         reminder.setSession(treatment.getSession());
    //         otherReminderRepo.save(reminder);
    //     }
    //     System.out.println("SNS Going To Work");

    //     String englishMessage = buildMedicineSms(
    //             saved,
    //             doctor,
    //             saved.getMedicines(),
    //             saved.getOtherTreatments(),
    //             true
    //     );

    //     String smsMessage = englishMessage;

    //     String language = saved.getPreferredLanguage();
    //     if (language != null && !language.equalsIgnoreCase("english")) {
    //         smsMessage = indicTransService.translate(englishMessage, language);
    //     }


    //     try {
    //         System.out.println("📨 Attempting to send SMS...");
    //         smsSender.sendSms(saved.getPhoneNo(), smsMessage); // unchanged
    //         System.out.println("✅ SMS sent successfully!");
    //     } catch (Exception e) {
    //         System.err.println("❌ Failed to send SMS via SNS: " + e.getMessage());
    //         e.printStackTrace();
    //     }

    //     return saved;
    // }

    private String normalizePhone(String phoneNo) {
    if (phoneNo == null) return null;
    if (phoneNo.startsWith("+91")) {
        return phoneNo.substring(3);
    }
    return phoneNo;
}
    public PatientInfo saveNewPatient(PatientInfo patient, int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

    if (!doctor.isApproved()) {
        throw new RuntimeException("Doctor is not approved by admin. Cannot add patients.");
    }

    String phoneNo = normalizePhone(patient.getPhoneNo());

    // 1. Check if patient already exists by phone number
    PatientInfo finalPatient = patientRepo.findByPhoneNo(phoneNo)
        .stream().findFirst() // Using your existing List<PatientInfo> findByPhoneNo
        .map(existing -> {
            // APPEND: Patient exists, so we add new data to the existing object
            if (patient.getDoctorExaminations() != null) {
                existing.getDoctorExaminations().addAll(patient.getDoctorExaminations());
            }
            if (patient.getMedicines() != null) {
                existing.getMedicines().addAll(patient.getMedicines());
            }
            if (patient.getOtherTreatments() != null) {
                existing.getOtherTreatments().addAll(patient.getOtherTreatments());
            }
            // Update basic info in case it changed
            existing.setAge(patient.getAge());
            existing.setName(patient.getName());
            existing.setDoctor(doctor);
            return existing;
        })
        .orElseGet(() -> {
            // CREATE: Patient is new
            patient.setDoctor(doctor);
            patient.setPhoneNo(phoneNo);
            return patient;
        });

    // 2. Save the final patient object (Update or Insert)
    PatientInfo saved = patientRepo.save(finalPatient);

    // 3. Create Reminders for ONLY the new items
    // (Note: To be perfect, you'd only create reminders for the 'incoming' lists, 
    // but for now, this will save the current set of reminders)
    saveReminders(saved, doctor);

    // 4. Update History
    saveToHistory(saved, "Visit recorded: " + (patient.getDoctorExaminations().isEmpty() ? "Checkup" : patient.getDoctorExaminations().get(0).getDiagnosis()));

    return saved;
    }

    private void saveToHistory(PatientInfo saved, String note) {
    PatientHistory history = new PatientHistory();
    history.setPhoneNo(saved.getPhoneNo());
    history.setPatientName(saved.getName());
    
    String visitTitle = "Visit on " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    String historyText = buildVisitHistory(visitTitle, saved.getMedicines(), saved.getOtherTreatments());
    
    history.setPatientHistory(historyText);
    patientHistoryRepository.save(history);
}

//     Doctor doctor = doctorRepo.findById(doctorId)
//             .orElseThrow(() -> new RuntimeException("Doctor not found"));

//     if (!doctor.isApproved()) {
//         throw new RuntimeException("Doctor is not approved by admin. Cannot add patients.");
//     }

//     patient.setDoctor(doctor);
//     patient.setPhoneNo(normalizePhone(patient.getPhoneNo()));

//     // Save patient
//     PatientInfo saved = patientRepo.save(patient);

//     // Save medicine reminders
//     for (Medicine med : saved.getMedicines()) {
//         MedicineReminder reminder = new MedicineReminder();
//         reminder.setPatientId(saved.getPatientId());
//         reminder.setPatientName(saved.getName());
//         reminder.setDoctorName(doctor.getDoctorName());
//         reminder.setMedicineName(med.getMedicineName());
//         reminder.setSession(med.getSession());
//         reminder.setTakingmethod(med.getTakingmethod());
//         reminder.setStartDate(med.getStartDate());
//         reminder.setLastDate(med.getLastDate());
//         reminder.setTotalQuantity(med.getTotalQuantity());
//         reminder.setQuantityPerSession(med.getQuantityPerSession());
//         medicineReminderRepo.save(reminder);
//     }

//     // Save other treatment reminders
//     for (OtherTreatment treatment : saved.getOtherTreatments()) {
//         OtherReminder reminder = new OtherReminder();
//         reminder.setPatientId(saved.getPatientId());
//         reminder.setPatientName(saved.getName());
//         reminder.setDoctorName(doctor.getDoctorName());
//         reminder.setTreatmentName(treatment.getTreatmentName());
//         reminder.setDateOfReminder(treatment.getDateOfReminder());
//         reminder.setSession(treatment.getSession());
//         otherReminderRepo.save(reminder);
//     }

//     // 🔥 CREATE INITIAL HISTORY ENTRY
//     PatientHistory history = new PatientHistory();
//     history.setPhoneNo(saved.getPhoneNo());
//     history.setPatientName(saved.getName());
//     history.setPatientHistory("Initial Visit: Patient registered.");
//     patientHistoryRepository.save(history);

//     // Send SMS
//     String smsMessage = buildMedicineSms(
//             saved,
//             doctor,
//             saved.getMedicines(),
//             saved.getOtherTreatments(),
//             true
//     );

//     if (saved.getPreferredLanguage() != null &&
//             !saved.getPreferredLanguage().equalsIgnoreCase("english")) {
//         smsMessage = indicTransService.translate(
//                 smsMessage,
//                 saved.getPreferredLanguage()
//         );
//     }

//     smsSender.sendSms(saved.getPhoneNo(), smsMessage);

//     // PatientHistory history = new PatientHistory();
// history.setPhoneNo(saved.getPhoneNo());
// history.setPatientName(saved.getName());

// String visitTitle = "Visit on " +
//         LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));


// String historyText = buildVisitHistory(
//         visitTitle,
//         saved.getMedicines(),
//         saved.getOtherTreatments()
// );

// history.setPatientHistory(historyText);
// patientHistoryRepository.save(history);


//     return saved;
// }

private void saveReminders(PatientInfo saved, Doctor doctor) {
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
}

    private String buildVisitHistory(
        String visitTitle,
        List<Medicine> medicines,
        List<OtherTreatment> treatments
) {
    StringBuilder sb = new StringBuilder();

    sb.append(visitTitle).append("\n");

    if (medicines != null && !medicines.isEmpty()) {
        sb.append("\nMedicines Prescribed:\n");
        for (Medicine med : medicines) {
            sb.append("- ")
              .append(med.getMedicineName())
              .append(" | ")
              .append(med.getSession())
              .append(" | ")
              .append(med.getQuantityPerSession())
              .append(" | ")
              .append(med.getTakingmethod())
              .append("\n");
        }
    }

    if (treatments != null && !treatments.isEmpty()) {
        sb.append("\nTreatments Prescribed:\n");
        for (OtherTreatment t : treatments) {
            sb.append("- ")
              .append(t.getTreatmentName())
              .append(" | ")
              .append(t.getSession())
              .append(" | ")
              .append(t.getDateOfReminder())
              .append("\n");
        }
    }

    return sb.toString();
}


    public Optional<PatientInfo> getPatientByName(String name) {
        return patientRepo.findByName(name);
    }

    // public PatientInfo addVisitToExistingPatient(PatientVisitDTO dto, int doctorId) {

    //     Doctor doctor = doctorRepo.findById(doctorId)
    //             .orElseThrow(() -> new RuntimeException("Doctor not found"));

    //     if (!doctor.isApproved()) {
    //         throw new RuntimeException("Doctor is not approved by admin. Cannot add patients.");
    //     }

    //     PatientInfo patient = patientRepo.findByName(dto.getPatientName())
    //             .orElseThrow(() -> new RuntimeException("Patient not found"));

    //     patient.setDoctor(doctor);

    //     // Append new DoctorExaminations
    //     if (dto.getDoctorExaminations() != null) {
    //         patient.getDoctorExaminations().addAll(dto.getDoctorExaminations());
    //     }

    //     // Append new Medicines and create reminders
    //     if (dto.getMedicines() != null) {
    //         for (Medicine med : dto.getMedicines()) {
    //             patient.getMedicines().add(med);

    //             MedicineReminder reminder = new MedicineReminder();
    //             reminder.setPatientId(patient.getPatientId());
    //             reminder.setPatientName(patient.getName());
    //             reminder.setDoctorName(patient.getDoctor().getDoctorName());
    //             reminder.setMedicineName(med.getMedicineName());
    //             reminder.setSession(med.getSession());
    //             reminder.setTakingmethod(med.getTakingmethod());
    //             reminder.setStartDate(med.getStartDate());
    //             reminder.setLastDate(med.getLastDate());
    //             reminder.setTotalQuantity(med.getTotalQuantity());
    //             reminder.setQuantityPerSession(med.getQuantityPerSession());
    //             medicineReminderRepo.save(reminder);
    //         }
    //     }

    //     // Append new OtherTreatments and create reminders
    //     if (dto.getOtherTreatments() != null) {
    //         for (OtherTreatment treatment : dto.getOtherTreatments()) {
    //             patient.getOtherTreatments().add(treatment);

    //             OtherReminder reminder = new OtherReminder();
    //             reminder.setPatientId(patient.getPatientId());
    //             reminder.setPatientName(patient.getName());
    //             reminder.setDoctorName(patient.getDoctor().getDoctorName());
    //             reminder.setTreatmentName(treatment.getTreatmentName());
    //             reminder.setDateOfReminder(treatment.getDateOfReminder());
    //             reminder.setSession(treatment.getSession());
    //             otherReminderRepo.save(reminder);
    //         }
    //     }
    //     String smsMessage = buildMedicineSms(patient, patient.getDoctor(), dto.getMedicines(), dto.getOtherTreatments(),
    //             false);
    //     smsSender.sendSms(patient.getPhoneNo(), smsMessage);

    //     return patientRepo.save(patient);
    // }
    public PatientInfo addVisitToExistingPatient(PatientVisitDTO dto, int doctorId) {

    Doctor doctor = doctorRepo.findById(doctorId)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

    if (!doctor.isApproved()) {
        throw new RuntimeException("Doctor is not approved by admin. Cannot add patients.");
    }

    String phoneNo = normalizePhone(dto.getPhoneNo());

    // 🔥 FIND PATIENT BY NAME + PHONE (NOT NAME ALONE)
    PatientInfo patient = patientRepo
            .findByNameAndPhoneNo(dto.getPatientName(), phoneNo)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

    patient.setDoctor(doctor);

    // Append doctor examinations
    if (dto.getDoctorExaminations() != null) {
        patient.getDoctorExaminations().addAll(dto.getDoctorExaminations());
    }

    // Append medicines and create reminders
    if (dto.getMedicines() != null) {
        for (Medicine med : dto.getMedicines()) {
            patient.getMedicines().add(med);

            MedicineReminder reminder = new MedicineReminder();
            reminder.setPatientId(patient.getPatientId());
            reminder.setPatientName(patient.getName());
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
    }

    // Append treatments and create reminders
    if (dto.getOtherTreatments() != null) {
        for (OtherTreatment treatment : dto.getOtherTreatments()) {
            patient.getOtherTreatments().add(treatment);

            OtherReminder reminder = new OtherReminder();
            reminder.setPatientId(patient.getPatientId());
            reminder.setPatientName(patient.getName());
            reminder.setDoctorName(doctor.getDoctorName());
            reminder.setTreatmentName(treatment.getTreatmentName());
            reminder.setDateOfReminder(treatment.getDateOfReminder());
            reminder.setSession(treatment.getSession());
            otherReminderRepo.save(reminder);
        }
    }

    // 🔥 APPEND HISTORY AS NEW VISIT (NEW ROW)
    PatientHistory history = new PatientHistory();
    history.setPhoneNo(patient.getPhoneNo());
    history.setPatientName(patient.getName());
    history.setPatientHistory("Follow-up Visit: Medicines / treatments updated.");
    patientHistoryRepository.save(history);

    // Send SMS
    String smsMessage = buildMedicineSms(
            patient,
            doctor,
            dto.getMedicines(),
            dto.getOtherTreatments(),
            false
    );
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

    public PatientVisitDTO getPatientVisitHistory(int patientId) {

    PatientInfo patient = patientRepo.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found"));

    PatientVisitDTO dto = new PatientVisitDTO();
    dto.setPatientName(patient.getName());

    // Doctor examinations (notes / diagnosis)
    dto.setDoctorExaminations(patient.getDoctorExaminations());

    // Current medicines (entity table)
    dto.setMedicines(patient.getMedicines());

    // Current treatments
    dto.setOtherTreatments(patient.getOtherTreatments());

    return dto;
}


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
