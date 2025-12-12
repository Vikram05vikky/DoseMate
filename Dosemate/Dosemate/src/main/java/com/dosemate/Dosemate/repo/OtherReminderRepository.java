package com.dosemate.Dosemate.repo;
import com.dosemate.Dosemate.model.OtherReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OtherReminderRepository extends JpaRepository<OtherReminder, Integer>
{
    List<OtherReminder> findByDateOfReminderBefore(LocalDate date);

    List<OtherReminder> findByDateOfReminder(LocalDate now);
}
