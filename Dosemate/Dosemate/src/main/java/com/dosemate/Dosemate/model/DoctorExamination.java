package com.dosemate.Dosemate.model;

import jakarta.persistence.*;

@Entity
public class DoctorExamination
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int problemId;
    private String patientProblem;
    private String doctorExamination;

//    @ManyToOne
//    @JoinColumn(name = "patient_id")
//    private PatientInfo patient;

    public String getPatientProblem() {
        return patientProblem;
    }

    public void setPatientProblem(String patientProblem) {
        this.patientProblem = patientProblem;
    }

    public String getDoctorExamination() {
        return doctorExamination;
    }

    public void setDoctorExamination(String doctorExamination) {
        this.doctorExamination = doctorExamination;
    }

//    public PatientInfo getPatient() {
//        return patient;
//    }
//
//    public void setPatient(PatientInfo patient) {
//        this.patient = patient;
//    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }



}
