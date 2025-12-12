package com.dosemate.Dosemate.service;

import com.dosemate.Dosemate.model.MedicineReminder;
import com.dosemate.Dosemate.model.OtherReminder;
import com.dosemate.Dosemate.repo.MedicineReminderRepository;
import com.dosemate.Dosemate.repo.OtherReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderCleanupService
{

    @Autowired
    private MedicineReminderRepository medicineReminderRepository;

    @Autowired
    private OtherReminderRepository otherTreatmentReminderRepository;

    // Runs daily at 10:00 PM
    @Scheduled(cron = "0 49 10 * * *")
    public void cleanUpExpiredReminders()
    {
        LocalDate today = LocalDate.now();

        // Delete expired medicine reminders
        List<MedicineReminder> expiredMedicines = medicineReminderRepository.findByLastDateBefore(today);
        medicineReminderRepository.deleteAll(expiredMedicines);

        // Delete expired other treatment reminders
        List<OtherReminder> expiredOthers = otherTreatmentReminderRepository.findByDateOfReminderBefore(today);
        otherTreatmentReminderRepository.deleteAll(expiredOthers);

        System.out.println("Reminder cleanup done at 12 AM for both Medicine and Other Treatment Database");
    }
}
