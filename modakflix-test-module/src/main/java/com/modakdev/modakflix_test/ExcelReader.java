package com.modakdev.modakflix_test;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public static List<Question> readQuestionsFromExcel(String filePath) {
        List<Question> questions = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                Question question = new Question();
                question.setQuestion(row.getCell(0).getStringCellValue());
                question.setOption1(row.getCell(1).getStringCellValue());
                question.setOption2(row.getCell(2).getStringCellValue());
                question.setOption3(row.getCell(3).getStringCellValue());
                question.setOption4(row.getCell(4).getStringCellValue());
                question.setAnswer(row.getCell(5).getStringCellValue());
                questions.add(question);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    public static List<Test> detectTests(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));

        List<Test> testTitles = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                testTitles.add(new Test(file.getName().replace(".xlsx", "")));
            }
        }

        return testTitles;
    }
}

