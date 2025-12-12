package com.dosemate.Dosemate.controller;
import com.dosemate.Dosemate.model.PatientHistory;
import com.dosemate.Dosemate.repo.PatientHistoryRepository;
import com.dosemate.Dosemate.service.OlamaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/olama")
public class OlamaController {

    private final OlamaService olamaService;
    private final PatientHistoryRepository patientHistoryRepository;

    public OlamaController(OlamaService olamaService, PatientHistoryRepository patientHistoryRepository) {
        this.olamaService = olamaService;
        this.patientHistoryRepository = patientHistoryRepository;
    }

    // POST request to get completion
    @PostMapping("/completion")
    public String getCompletion(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        return olamaService.getCompletion(prompt);
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzePatientCondition(@RequestBody Map<String, String> request) {
        String phoneNo = request.get("phoneNo");
        String patientName = request.get("patientName");
        String currentProblem = request.get("currentProblem");

        // 1️⃣ Check if phone number exists
        List<PatientHistory> histories = patientHistoryRepository.findAll()
                .stream()
                .filter(h -> h.getPhoneNo().equals(phoneNo))
                .toList();

        if (histories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ No patient found with this phone number.");
        }

        // 2️⃣ Check if name matches
        boolean nameMatch = histories.stream()
                .anyMatch(h -> h.getPatientName().equalsIgnoreCase(patientName));

        if (!nameMatch) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("⚠️ This phone number is not mapped with this patient name.");
        }

        // 3️⃣ Get the latest patient history
        PatientHistory latestHistory = histories.get(histories.size() - 1);
        String patientPastHistory = latestHistory.getPatientHistory();

        // 4️⃣ Create a detailed prompt for Ollama
        String combinedPrompt = String.format("""
                You are an AI medical assistant.
                Analyze the following patient's past medical history and the current reported problem.

                🧍‍♂️ Patient Name: %s
                📞 Phone No: %s

                🩺 Past Medical History:
                %s

                🚨 Current Problem:
                %s

                Based on the above:
                1. Check if the current problem may be related to the past medical history.
                2. Suggest possible causes or related conditions.
                3. Suggest any necessary lab tests or diagnostics — explain briefly *why each test is relevant*.
                4. Provide the reasoning clearly in 4 to 6 lines.
                """, patientName, phoneNo, patientPastHistory, currentProblem);

        // 5️⃣ Send to Ollama and return AI analysis
        String aiResponse = olamaService.getCompletion(combinedPrompt);

        return ResponseEntity.ok(aiResponse);
    }
}
