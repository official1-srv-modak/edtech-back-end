package com.modakdev.modakflix_test;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private static final String TESTS_FOLDER = "src/main/resources/tests/";

    public List<Test> getAvailableTests() {
        return ExcelReader.detectTests(TESTS_FOLDER);
    }

    public List<Question> getQuestionsForTest(String testName) {
        String filePath = TESTS_FOLDER + testName + ".xlsx";
        return ExcelReader.readQuestionsFromExcel(filePath);
    }
}

