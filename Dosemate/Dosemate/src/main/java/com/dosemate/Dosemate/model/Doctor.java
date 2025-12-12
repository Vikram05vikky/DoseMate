// package com.dosemate.Dosemate.model;

// import com.fasterxml.jackson.annotation.JsonManagedReference;
// import jakarta.persistence.*;

// import java.util.ArrayList;
// import java.util.List;

// @Entity
// public class Doctor {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private int doctorId;
//     private String email;
//     private String doctorName;
//     private String password;

//     private boolean approved = false;

//     @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
//     @JsonManagedReference
//     private List<PatientInfo> patients = new ArrayList<>();

//     public boolean isApproved() {
//         return approved;
//     }

//     public void setApproved(boolean approved) {
//         this.approved = approved;
//     }

//     public int getDoctorId() {
//         return doctorId;
//     }

//     public void setDoctorId(int doctorId) {
//         this.doctorId = doctorId;
//     }

//     public String getDoctorName() {
//         return doctorName;
//     }

//     public void setDoctorName(String doctorName) {
//         this.doctorName = doctorName;
//     }

//     public String getPassword() {
//         return password;
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public List<PatientInfo> getPatients() {
//         return patients;
//     }

//     public void setPatients(List<PatientInfo> patients) {
//         this.patients = patients;
//     }

// }
package com.dosemate.Dosemate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorId;

    private String doctorName;
    @Column(nullable = false, unique = true)
    private String doctorEmail; // ✅ New field for login
    private String password;
    private boolean approved = false;

    private String phone;
    private String specialization;
    private String licenseNumber;
    private String registrationDate;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PatientInfo> patients = new ArrayList<>();

    // Getters and setters
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public List<PatientInfo> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientInfo> patients) {
        this.patients = patients;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
