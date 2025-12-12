package com.dosemate.Dosemate.repo;

import com.dosemate.Dosemate.model.PatientHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientHistoryRepository extends JpaRepository<PatientHistory, Long> {
}
