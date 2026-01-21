// package com.dosemate.Dosemate.controller;

// import com.dosemate.Dosemate.model.PatientHistory;
// import com.dosemate.Dosemate.model.PatientInfo;
// import com.dosemate.Dosemate.repo.PatientHistoryRepository;
// import com.dosemate.Dosemate.repo.PatientInfoRepository;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/patient/history")
// @CrossOrigin(origins = "http://localhost:5173")
// public class PatientHistoryController {

//     private final PatientInfoRepository patientInfoRepository;
//     private final PatientHistoryRepository patientHistoryRepository;

//     public PatientHistoryController(PatientInfoRepository patientInfoRepository,
//             PatientHistoryRepository patientHistoryRepository) {
//         this.patientInfoRepository = patientInfoRepository;
//         this.patientHistoryRepository = patientHistoryRepository;
//     }

//     @GetMapping("/all")
//     public ResponseEntity<List<PatientHistory>> getAllPatientHistory() {
//         List<PatientHistory> history = patientHistoryRepository.findAll();
//         return ResponseEntity.ok(history);
//     }

//     @PostMapping("/add")
//     public ResponseEntity<?> addPatientHistory(@RequestBody Map<String, String> request) {
//         String phoneNo = request.get("phoneNo");
//         String patientName = request.get("patientName");
//         String patientHistoryText = request.get("patientHistory");

//         // Check if phone number exists
//         List<PatientInfo> patients = patientInfoRepository.findByPhoneNo(phoneNo);
//         if (patients.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                     .body("No patient has enrolled with this phone number.");
//         }

//         // Check if name matches the phone number
//         boolean nameMatch = patients.stream()
//                 .anyMatch(p -> p.getName().equalsIgnoreCase(patientName));
//         if (!nameMatch) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                     .body("This phone number is not mapped with this name.");
//         }

//         // Save history
//         PatientHistory patientHistory = new PatientHistory();
//         patientHistory.setPhoneNo(phoneNo);
//         patientHistory.setPatientName(patientName);
//         patientHistory.setPatientHistory(patientHistoryText);

//         patientHistoryRepository.save(patientHistory);

//         return ResponseEntity.ok("Patient history added successfully.");
//     }

//     @PostMapping("/append")
//     public ResponseEntity<?> appendPatientHistory(@RequestBody Map<String, String> request) {
//         String phoneNo = request.get("phoneNo");
//         String patientName = request.get("patientName");
//         String newHistoryText = request.get("newHistory");

//         // Check if phone number exists in PatientInfo table
//         List<PatientInfo> patients = patientInfoRepository.findByPhoneNo(phoneNo);
//         if (patients.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                     .body("❌ No patient has enrolled with this phone number.");
//         }

//         // Check if name matches the phone number
//         boolean nameMatch = patients.stream()
//                 .anyMatch(p -> p.getName().equalsIgnoreCase(patientName));
//         if (!nameMatch) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                     .body("⚠️ This phone number is not mapped with this name.");
//         }

//         // Find existing patient history (based on phone number)
//         Optional<PatientHistory> existingHistory = patientHistoryRepository.findAll()
//                 .stream()
//                 .filter(h -> h.getPhoneNo().equals(phoneNo) && h.getPatientName().equalsIgnoreCase(patientName))
//                 .reduce((first, second) -> second); // get latest if multiple

//         if (existingHistory.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                     .body("⚠️ No existing history found for this patient. Please use /add first.");
//         }

//         // Append new history text
//         PatientHistory history = existingHistory.get();
//         String updatedHistory = history.getPatientHistory() + "\n" + newHistoryText;
//         history.setPatientHistory(updatedHistory);
//         patientHistoryRepository.save(history);

//         return ResponseEntity.ok("✅ Patient history appended successfully.");

//     }
// }

package com.dosemate.Dosemate.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dosemate.Dosemate.model.PatientHistory;
import com.dosemate.Dosemate.model.PatientInfo;
import com.dosemate.Dosemate.repo.PatientHistoryRepository;
import com.dosemate.Dosemate.repo.PatientInfoRepository;

@RestController
@RequestMapping("/api/patient/history")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientHistoryController {

    private final PatientInfoRepository patientInfoRepository;
    private final PatientHistoryRepository patientHistoryRepository;

    public PatientHistoryController(PatientInfoRepository patientInfoRepository,
            PatientHistoryRepository patientHistoryRepository) {
        this.patientInfoRepository = patientInfoRepository;
        this.patientHistoryRepository = patientHistoryRepository;
    }

    // ✅ Get all histories
    @GetMapping("/all")
    public ResponseEntity<List<PatientHistory>> getAllPatientHistory() {
        List<PatientHistory> history = patientHistoryRepository.findAll();
        return ResponseEntity.ok(history);
    }

    // ✅ Get history by patient phone number
    // @GetMapping("/{phoneNo}")
    // public ResponseEntity<?> getPatientHistoryByPhone(@PathVariable String phoneNo) {
    //     List<PatientHistory> historyList = patientHistoryRepository.findAll()
    //             .stream()
    //             .filter(h -> h.getPhoneNo().equals(phoneNo))
    //             .toList();

    //     if (historyList.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                 .body("No history found for this patient.");
    //     }

    //     return ResponseEntity.ok(historyList);
    // }
    private String normalizePhone(String phoneNo) {
    if (phoneNo.startsWith("+91")) {
        return phoneNo.substring(3);
    }
    return phoneNo;
}

    @GetMapping("/{phoneNo}")
public ResponseEntity<?> getPatientHistoryByPhone(@PathVariable String phoneNo) {

     phoneNo = normalizePhone(phoneNo);

    List<PatientHistory> historyList =
            patientHistoryRepository.findByPhoneNoOrderByIdDesc(phoneNo);

    if (historyList.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No history found for this patient.");
    }

    return ResponseEntity.ok(historyList);
}

    // ✅ Add new history entry
    @PostMapping("/add")
    public ResponseEntity<?> addPatientHistory(@RequestBody Map<String, String> request) {
        String phoneNo = request.get("phoneNo");
        phoneNo = normalizePhone(phoneNo);
        String patientName = request.get("patientName");
        String patientHistoryText = request.get("patientHistory");
        

        // Check if phone number exists
        Optional<PatientInfo> patients = patientInfoRepository.findByPhoneNo(phoneNo);
        if (patients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No patient has enrolled with this phone number.");
        }

        // Check if name matches the phone number
        boolean nameMatch = patients.stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(patientName));
        if (!nameMatch) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("This phone number is not mapped with this name.");
        }

        // Save history
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setPhoneNo(phoneNo);
        patientHistory.setPatientName(patientName);
        patientHistory.setPatientHistory(patientHistoryText);
        patientHistory.setCreatedDate(new Date());

        patientHistoryRepository.save(patientHistory);

        return ResponseEntity.ok("Patient history added successfully.");
    }

    // ✅ Append new history entry
   @PostMapping("/append")
public ResponseEntity<?> appendPatientHistory(@RequestBody Map<String, String> request) {

    String phoneNo = normalizePhone(request.get("phoneNo"));
    String patientName = request.get("patientName");
    String newHistoryText = request.get("newHistory");

    // Validate patient exists
    Optional<PatientInfo> patients = patientInfoRepository.findByPhoneNo(phoneNo);
    boolean nameMatch = patients.stream()
            .anyMatch(p -> p.getName().equalsIgnoreCase(patientName));

    if (!nameMatch) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("⚠️ Phone number and patient name do not match.");
    }

    // 🔥 CREATE NEW HISTORY ROW (NEW VISIT)
    PatientHistory history = new PatientHistory();
    history.setPhoneNo(phoneNo);
    history.setPatientName(patientName);
    history.setPatientHistory(newHistoryText);

    patientHistoryRepository.save(history);

    return ResponseEntity.ok("✅ Patient history appended as new visit.");
}

}
