package com.dosemate.Dosemate.controller;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dosemate.Dosemate.model.PatientHistory;
import com.dosemate.Dosemate.repo.PatientHistoryRepository;
import com.dosemate.Dosemate.service.OlamaService;

@RestController
@CrossOrigin(origins = "*")
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

Your task is to analyze the patient’s past medical history and current complaint
and generate a structured medical analysis strictly based on the information provided.

IMPORTANT RULES (MUST FOLLOW):
- Do NOT assume or invent patient-specific facts.
- Do NOT include age, gender, vitals, dates, hospital names, or doctor names unless explicitly provided.
- Do NOT provide a final diagnosis.
- Do NOT prescribe medications or dosages.
- If information is insufficient for a section:
  • First clearly state: "Insufficient information available."
  • Then you MAY provide a possible explanation or consideration,
    BUT it MUST be explicitly labeled as:
    "Assumption based on general medical knowledge due to lack of specific information."

PATIENT DETAILS
Name:
%s

PAST MEDICAL HISTORY
%s

CURRENT PROBLEM
%s

MANDATORY REPORT STRUCTURE  
(All headings below MUST be included exactly and in this order)

1. Clinical Summary
Provide a brief overview of the patient based on past medical history.
If insufficient data exists, state so and then give a general assumption with proper labeling.

2. Current Problem Analysis
Analyze the current complaint using available details.
If details are limited, clearly indicate assumptions as described above.

3. Correlation With Past Medical History
State whether the current problem appears related to past history.
If unclear, explicitly state uncertainty and provide a labeled assumption.

4. Possible Causes and Differential Considerations
List possible causes.
If insufficient data exists, provide general possibilities clearly marked as assumptions.

5. Recommended Lab Tests and Diagnostics
Suggest general investigations.
If unclear, state insufficiency and provide assumption-based suggestions.

6. Treatment Assistance (General Guidance Only)
Provide non-prescriptive, general management guidance.
If insufficient information exists, include assumption-based guidance with clear labeling.

7. Risk Assessment
Assess potential risks.
If risk level cannot be determined, state so and provide assumption-based considerations.

8. Follow-up Advice
Suggest general follow-up steps.
If unclear, provide assumption-based follow-up advice.

9. Patient Education
Provide general educational points.
If specific education cannot be tailored, state insufficiency and provide general advice as an assumption.

10. Disclaimer
State that this analysis is AI-generated, assumption-based where indicated,
and does not constitute a medical diagnosis.

FORMAT RULES:
- Use headings exactly as listed.
- Clearly separate factual statements from assumptions.
- Never present assumptions as confirmed findings.
- Maintain professional medical tone.
""",
patientName,
patientPastHistory,
currentProblem
);




        // 5️⃣ Send to Ollama and return AI analysis
        String aiResponse = olamaService.getCompletion(combinedPrompt);

        return ResponseEntity.ok(aiResponse);
    }
}
