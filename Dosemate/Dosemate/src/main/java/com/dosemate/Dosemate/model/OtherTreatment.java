package com.dosemate.Dosemate.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OtherTreatment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lsiId;
    private String treatmentName;
    private LocalDate dateOfReminder;
    private String session;

//    @ManyToOne
//    @JoinColumn(name = "patient_id")
//    private PatientInfo patient;

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public int getLsiId() {
        return lsiId;
    }

    public void setLsiId(int lsiId) {
        this.lsiId = lsiId;
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

//    public PatientInfo getPatient() {
//        return patient;
//    }
//
//    public void setPatient(PatientInfo patient) {
//        this.patient = patient;
//    }



}
