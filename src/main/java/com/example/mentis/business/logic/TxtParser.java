package com.example.mentis.business.logic;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Voxel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TxtParser {

    private final static Logger log = LoggerFactory.getLogger(TxtParser.class);

    public static void testParse() {
        log.info("test parsing");
        int count = 0;
        try(Scanner scanner = new Scanner(new File("src/main/resources/txt.txt"))) {
            skipToLine15(scanner);

            createTestExcel(scanner, Arrays.stream(scanner.nextLine().split("\\s+")).map(String::strip).toArray(String[]::new));

        } catch (IOException e) {
            log.error("Exception while parsing txt: " + e.getMessage());
        }
    }

    private static void createTestExcel(Scanner scanner, String[] ths) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("testSheet");

        createTableHeads(sheet, ths);
        skipTo(scanner, "Frequencies");
        writeValues(scanner, sheet, 1);
        skipTo(scanner, "pH");
        writePhValues(scanner, sheet);
        writeVoxelValues(DataManager.getProjectById(3).getMembers().get(0).getExaminations().get(0), sheet);

        autosize(sheet);

        // Ausgabe in Datei
        try (FileOutputStream fileOut = new FileOutputStream("mentisData/test.xlsx")) {
            workbook.write(fileOut);
            log.info("Excel-File finished!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writePhValues(Scanner scanner, Sheet sheet) {
        int rowNum = 1;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] values = line.split("\t");
            Row row = sheet.getRow(rowNum);

            row.createCell(8).setCellValue(values[0].strip());
            row.createCell(9).setCellValue(values[2].strip());
            rowNum++;
        }
    }

    private static void autosize(Sheet sheet) {
        for (int i = 0; i < 30; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void writeVoxelValues(Examination e,Sheet sheet) {
        if (e == null) {
            log.error("exam is null");
            return;
        }
        Voxel[] voxels = e.getVoxels();
        for (int i = 0; i < voxels.length; i++) {
            Row row = sheet.getRow(i + 1);
            row.createCell(0).setCellValue("12");
            row.createCell(1).setCellValue(e.getExam());
            row.createCell(2).setCellValue(e.getSlice());
            row.createCell(3).setCellValue((voxels[i].getArea() == null) ? "not mapped" : voxels[i].getArea().name());
            row.createCell(4).setCellValue(i);
            row.createCell(5).setCellValue((voxels[i].getRow() + 1) + ";" + (voxels[i].getCol() + 1));
            row.createCell(6).setCellValue(voxels[i].getValidationStatus().name());
        }
    }

    private static void writeValues(Scanner scanner, Sheet sheet, int rowNumber) {
        int count = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (!line.matches("^[+-]?\\d*(\\.\\d+)?[eE][+-]?\\d+.*")) {
                break;
            }
            count++;
            Row row = sheet.createRow(rowNumber);
            rowNumber++;
            String[] values = line.split("\\s+");
            for (int i = 0; i < values.length; i++) {
                row.createCell(11 + i).setCellValue(values[i].strip());
            }
        }
        log.info("found " + count + " value lines");
    }

    private static void skipTo(Scanner scanner, String startsWith) {
        int i = 0;
        while (scanner.hasNext() && !scanner.nextLine().startsWith(startsWith)){}
    }

    private static void createTableHeads(Sheet sheet, String[] ths) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("pat (ID)");
        row.createCell(1).setCellValue("examination");
        row.createCell(2).setCellValue("slice");
        row.createCell(3).setCellValue("region");
        row.createCell(4).setCellValue("voxel number");
        row.createCell(5).setCellValue("coordinate");
        row.createCell(6).setCellValue("validation");

        row.createCell(8).setCellValue("pH");
        row.createCell(9).setCellValue("Mg2+");

        for (int i = 0; i < ths.length; i++) {
            row.createCell(11 + i).setCellValue(ths[i]);
        }
    }

    private static void skipToLine15(Scanner scanner) {
        for (int i = 0; i < 14; i++) {
            scanner.nextLine();
        }
    }

}
