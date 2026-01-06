// package com.dosemate.Dosemate.model;

// import jakarta.persistence.*;

// @Entity
// public class DoctorExamination
// {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private int problemId;
//     private String patientProblem;
//     private String doctorExamination;

// //    @ManyToOne
// //    @JoinColumn(name = "patient_id")
// //    private PatientInfo patient;

//     public String getPatientProblem() {
//         return patientProblem;
//     }

//     public void setPatientProblem(String patientProblem) {
//         this.patientProblem = patientProblem;
//     }

//     public String getDoctorExamination() {
//         return doctorExamination;
//     }

//     public void setDoctorExamination(String doctorExamination) {
//         this.doctorExamination = doctorExamination;
//     }

// //    public PatientInfo getPatient() {
// //        return patient;
// //    }
// //
// //    public void setPatient(PatientInfo patient) {
// //        this.patient = patient;
// //    }

//     public int getProblemId() {
//         return problemId;
//     }

//     public void setProblemId(int problemId) {
//         this.problemId = problemId;
//     }



// }

package com.dosemate.Dosemate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DoctorExamination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int problemId;
    
    // Match these to your frontend JSON keys
    private String diagnosis; 
    private String symptoms;
    private String severityLevel;

    // Standard Getters and Setters
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(String severityLevel) { this.severityLevel = severityLevel; }

    public int getProblemId() { return problemId; }
    public void setProblemId(int problemId) { this.problemId = problemId; }
}