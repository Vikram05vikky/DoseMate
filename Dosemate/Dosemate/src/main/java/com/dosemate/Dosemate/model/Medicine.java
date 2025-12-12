package com.dosemate.Dosemate.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Medicine
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicineNo;
    private String medicineName;
    private int quantityPerSession;
    private int totalQuantity;
    private String session; // Morning, Afternoon, etc.
    private String takingmethod;
    private LocalDate startDate;
    private LocalDate lastDate;

    public String getTakingmethod() {
        return takingmethod;
    }

    public void setTakingmethod(String takingmethod) {
        this.takingmethod = takingmethod;
    }


//    @ManyToOne
//    @JoinColumn(name = "patient_id")
//    private PatientInfo patient;

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getMedicineNo() {
        return medicineNo;
    }

    public void setMedicineNo(int medicineNo) {
        this.medicineNo = medicineNo;
    }

    public int getQuantityPerSession() {
        return quantityPerSession;
    }

    public void setQuantityPerSession(int quantityPerSession) {
        this.quantityPerSession = quantityPerSession;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

//    public PatientInfo getPatient() {
//        return patient;
//    }
//
//    public void setPatient(PatientInfo patient) {
//        this.patient = patient;
//    }



}
