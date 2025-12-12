package com.dosemate.Dosemate.repo;
import com.dosemate.Dosemate.model.DoctorExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorExaminationRepository extends JpaRepository<DoctorExamination, Integer>
{

}