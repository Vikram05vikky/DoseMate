package com.dosemate.Dosemate.service;

import com.dosemate.Dosemate.model.MedicineReminder;
import com.dosemate.Dosemate.repo.MedicineReminderRepository;
import com.dosemate.Dosemate.repo.PatientInfoRepository;
import com.dosemate.Dosemate.service.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReminderScheduler {

    @Autowired
    private MedicineReminderRepository reminderRepo;

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private PatientInfoRepository patientRepo;

    // Map the time slot to session and greeting
    private void sendReminders(String sessionKeyword, String greeting) {
        List<MedicineReminder> reminders = reminderRepo.findBySessionContainingIgnoreCase(sessionKeyword);

        for (MedicineReminder reminder : reminders) {
            String message = greeting + " " + reminder.getPatientName() + ",\n"
                    + "Please take your medicine:\n"
                    + "Name: " + reminder.getMedicineName() + "\n"
                    + "Qty: " + reminder.getQuantityPerSession() + "\n"
                    + "Method: " + reminder.getTakingmethod();
            var patientOpt = patientRepo.findById(reminder.getPatientId());
            if (patientOpt.isPresent()) {
                String phoneNo = patientOpt.get().getPhoneNo();
                System.out.println(phoneNo);
                smsSender.sendSms(phoneNo, message);
            }

        }
    }

    // Morning reminders at 8 AM & 9 AM
    @Scheduled(cron = "0 51 8,9 * * *")
    public void sendMorningReminders() {
        System.out.println("Send morning reminders");
        sendReminders("morning", "🌅 Good morning");
    }

    // Afternoon reminders at 12 PM & 1 PM
    @Scheduled(cron = "0 22 14,13 * * *")
    public void sendAfternoonReminders() {
        sendReminders("afternoon", "🌞 Good afternoon");
    }

    // Evening reminders at 6 PM & 7 PM
    @Scheduled(cron = "0 53 14,19 * * *")
    public void sendEveningReminders() {
        sendReminders("evening", "🌇 Good evening");
    }

    // Night reminders at 8 PM & 9 PM
    @Scheduled(cron = "0 25 19 * * *")
    public void sendNightReminders() {
        sendReminders("night", "🌙 Good night");
    }
}
