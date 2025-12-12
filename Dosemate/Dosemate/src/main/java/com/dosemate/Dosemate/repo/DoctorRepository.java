// package com.dosemate.Dosemate.repo;

// import com.dosemate.Dosemate.model.Doctor;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
//     List<Doctor> findByApproved(boolean approved);

//     Doctor findByDoctorNameAndPassword(String doctorName, String password);

//     long countByApproved(boolean approved);

//     Doctor findByEmailAndPassword(String email, String password);

// }

package com.dosemate.Dosemate.repo;

import com.dosemate.Dosemate.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByApproved(boolean approved);

    Doctor findByDoctorEmailAndPassword(String doctorEmail, String password); // ✅ changed

    long countByApproved(boolean approved);
}
