package com.dosemate.Dosemate.DTO;

import com.dosemate.Dosemate.model.DoctorExamination;
import com.dosemate.Dosemate.model.Medicine;
import com.dosemate.Dosemate.model.OtherTreatment;

import java.util.List;

public class PatientVisitDTO
{
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    private String patientName;

    public List<DoctorExamination> getDoctorExaminations() {
        return doctorExaminations;
    }

    public void setDoctorExaminations(List<DoctorExamination> doctorExaminations) {
        this.doctorExaminations = doctorExaminations;
    }

    public List<OtherTreatment> getOtherTreatments() {
        return otherTreatments;
    }

    public void setOtherTreatments(List<OtherTreatment> otherTreatments) {
        this.otherTreatments = otherTreatments;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    private List<DoctorExamination> doctorExaminations;
    private List<Medicine> medicines;
    private List<OtherTreatment> otherTreatments;

}
