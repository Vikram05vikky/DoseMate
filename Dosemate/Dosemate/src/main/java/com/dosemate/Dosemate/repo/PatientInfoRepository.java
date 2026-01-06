package com.dosemate.Dosemate.repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dosemate.Dosemate.model.PatientInfo;

@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Integer>
{
    Optional<PatientInfo> findByName(String name);
    Optional<PatientInfo> findByPhoneNo(String phoneNo);

    // ✅ Count how many patients are linked to a specific doctor
    @Query("SELECT COUNT(p) FROM PatientInfo p WHERE p.doctor.doctorId = :doctorId")
    long countPatientsByDoctorId(@Param("doctorId") int doctorId);

    // ✅ List of patients for a specific doctor
    List<PatientInfo> findByDoctor_DoctorId(int doctorId);
    Optional<PatientInfo> findByNameAndPhoneNo(String name, String phoneNo);

    

}
