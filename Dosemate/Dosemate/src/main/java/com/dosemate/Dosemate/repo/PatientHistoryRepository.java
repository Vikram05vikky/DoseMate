package com.dosemate.Dosemate.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dosemate.Dosemate.model.PatientHistory;

public interface PatientHistoryRepository extends JpaRepository<PatientHistory, Long> {
     List<PatientHistory> findByPhoneNoOrderByIdDesc(String phoneNo);

     Optional<PatientHistory> findTopByPhoneNoAndPatientNameOrderByIdDesc(
            String phoneNo,
            String patientName
    );
}

