package com.dosemate.Dosemate.service;

import com.dosemate.Dosemate.model.OtherReminder;
import com.dosemate.Dosemate.repo.OtherReminderRepository;
import com.dosemate.Dosemate.repo.PatientInfoRepository;
import com.dosemate.Dosemate.service.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OtherReminderScheduler {

    @Autowired
    private OtherReminderRepository reminderRepository;

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private PatientInfoRepository patientRepo;

    private static final String TEMPLATE = "Hello %s, as doctor advised you to meet him with %s today. Please visit us to keep the health with care.";

    // Run at 8:00 AM
    @Scheduled(cron = "0 06 15 * * *")
    public void sendReminderAt8AM() {
        sendReminders();
    }

    // Run at 12:30 PM
    @Scheduled(cron = "0 52 14 * * *")
    public void sendReminderAt12_30PM() {
        sendReminders();
    }

    // Run at 4:00 PM
    @Scheduled(cron = "0 53 14 * * *")
    public void sendReminderAt4PM() {
        sendReminders();
    }

    // Run at 6:00 PM
    @Scheduled(cron = "0 55 14 * * *")
    public void sendReminderAt6PM() {
        sendReminders();
    }

    private void sendReminders() {
        List<OtherReminder> reminders = reminderRepository.findByDateOfReminder(LocalDate.now());
        for (OtherReminder reminder : reminders) {
            String message = String.format(TEMPLATE, reminder.getPatientName(), reminder.getTreatmentName());
            // You can add patient phone number logic accordingly
            var patientOpt = patientRepo.findById(reminder.getPatientId());
            if (patientOpt.isPresent()) {
                String phoneNo = patientOpt.get().getPhoneNo();
                System.out.println(phoneNo);
                smsSender.sendSms(phoneNo, message);
            }
        }
    }


}
