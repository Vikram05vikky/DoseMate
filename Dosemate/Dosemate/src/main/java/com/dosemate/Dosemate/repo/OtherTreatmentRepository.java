package com.dosemate.Dosemate.repo;
import com.dosemate.Dosemate.model.OtherTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherTreatmentRepository extends JpaRepository<OtherTreatment, Integer>
{

}
