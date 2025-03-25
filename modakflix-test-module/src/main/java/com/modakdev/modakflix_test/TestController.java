package com.modakdev.modakflix_test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/get-tests")
    public ResponseEntity<List<Test>> getAvailableTests() {
        return ResponseEntity.ok(testService.getAvailableTests());
    }

    @GetMapping("/get-test-questions/{testName}")
    public ResponseEntity<List<Question>> getTestQuestions(@PathVariable String testName) {
        return ResponseEntity.ok(testService.getQuestionsForTest(testName));
    }

    @PostMapping("/record-test")
    public ResponseEntity<String> recordTestResult(@RequestBody TestResult testResult) {
        System.out.println("Recorded Test Result: " + testResult.getUsername() + " - " + testResult.getMarks());
        return ResponseEntity.ok("Test result recorded successfully!");
    }
}

