package com.dosemate.Dosemate.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OtherReminder
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reminderId;
    private int patientId;
    private String patientName;
    private String doctorName;
    private String treatmentName;
    private LocalDate dateOfReminder;
    private String session;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public LocalDate getDateOfReminder() {
        return dateOfReminder;
    }

    public void setDateOfReminder(LocalDate dateOfReminder) {
        this.dateOfReminder = dateOfReminder;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }



}