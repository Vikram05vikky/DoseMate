package com.dosemate.Dosemate.controller;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dosemate.Dosemate.model.PatientHistory;
import com.dosemate.Dosemate.repo.PatientHistoryRepository;
import com.dosemate.Dosemate.service.OlamaService;

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

Analyze the patient's details and generate a structured medical report.

IMPORTANT RULE:
Every subheading listed below MUST be included in your response.
Do not skip any section. If information is missing, mention "Insufficient information available".

Patient Details
Name: %s

Past Medical History
%s

Current Complaint
%s

Report Subheadings (ALL MUST BE PRESENT)

1. Clinical Summary

2. Correlation Analysis

3. Possible Causes and Differential Diagnosis

4. Recommended Lab Tests and Diagnostics

5. Risk Assessment

6. Treatment Suggestions (General Guidance Only)

7. Follow-up Advice

8. Preventive Measures

9. Patient Education

10. Disclaimer

Instructions
Use clear headings.
Keep the report detailed.
Do not provide final diagnosis.
Do not prescribe medications.

""", patientName, patientPastHistory, currentProblem);



        // 5️⃣ Send to Ollama and return AI analysis
        String aiResponse = olamaService.getCompletion(combinedPrompt);

        return ResponseEntity.ok(aiResponse);
    }
}
