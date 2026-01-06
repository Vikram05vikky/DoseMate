package com.dosemate.Dosemate.repo;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dosemate.Dosemate.model.MedicineReminder;

@Repository
public interface MedicineReminderRepository extends JpaRepository<MedicineReminder, Integer>
{
    List<MedicineReminder> findByLastDateBefore(LocalDate date);

    @Query("SELECT m FROM MedicineReminder m WHERE LOWER(m.session) LIKE LOWER(CONCAT('%', :sessionKeyword, '%'))")
    List<MedicineReminder> findBySessionContainingIgnoreCase(@Param("sessionKeyword") String sessionKeyword);

    // ✅ Get distinct patient IDs for active treatments under a specific doctor
    @Query("SELECT DISTINCT m.patientId FROM MedicineReminder m WHERE m.doctorName = :doctorName")
    List<Integer> findActivePatientIdsByDoctorName(@Param("doctorName") String doctorName);

    // Count distinct patients globally (active treatments)
    @Query("SELECT COUNT(DISTINCT m.patientId) FROM MedicineReminder m")
    long countDistinctPatients();

       List<MedicineReminder> findByDoctorName(String doctorName);
}
