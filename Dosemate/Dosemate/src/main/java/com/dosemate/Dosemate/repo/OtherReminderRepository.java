package com.dosemate.Dosemate.repo;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dosemate.Dosemate.model.OtherReminder;

@Repository
public interface OtherReminderRepository extends JpaRepository<OtherReminder, Integer>
{
    List<OtherReminder> findByDateOfReminderBefore(LocalDate date);

    List<OtherReminder> findByDateOfReminder(LocalDate now);

    List<OtherReminder> findByDoctorName(String doctorName);

}
