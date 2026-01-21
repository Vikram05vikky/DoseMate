package com.dosemate.Dosemate.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity

public class PatientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;
    private String name;
    @Column(unique = true)
    private String phoneNo;
    private String email;
    private String whatsappNo;
    private String preferredLanguage;
    private int age;


    // ✅ NEW FIELD 2: Gender
    private String gender;

    // ✅ NEW FIELD 3: Registered Date (Set automatically on creation)
    @CreationTimestamp
    @Column(updatable = false) // Ensures the registration date is never overwritten
    private LocalDateTime registeredDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    private Doctor doctor;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DoctorExamination> doctorExaminations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Medicine> medicines = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<OtherTreatment> otherTreatments = new ArrayList<>();

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        this.whatsappNo = whatsappNo;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public List<DoctorExamination> getDoctorExaminations() {
        return doctorExaminations;
    }

    public void setDoctorExaminations(List<DoctorExamination> doctorExaminations) {
        this.doctorExaminations = doctorExaminations;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public List<OtherTreatment> getOtherTreatments() {
        return otherTreatments;
    }

    public void setOtherTreatments(List<OtherTreatment> otherTreatments) {
        this.otherTreatments = otherTreatments;
    }
    public String getPreferredLanguage() {
    return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }


}
