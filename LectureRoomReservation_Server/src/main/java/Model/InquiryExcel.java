/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import Controller.professor.Inquiry;

public class InquiryExcel {
    private  String EXCEL_FILE_PATH = "Inquiry.xlsx";


    public InquiryExcel() {}

    // 전체 문의 리스트 반환
    public List < Inquiry > loadAllInquiries() {
        List < Inquiry > inquiries = new ArrayList < > ();
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH); Workbook workbook = new XSSFWorkbook(fis)) {

            DataFormatter dataFormatter = new DataFormatter();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 헤더 행 건너뛰기
                Row row = sheet.getRow(i);
                String name = dataFormatter.formatCellValue(row.getCell(0));
                String id = dataFormatter.formatCellValue(row.getCell(1));
                String message = dataFormatter.formatCellValue(row.getCell(2));
                String dateStr = dataFormatter.formatCellValue(row.getCell(3));


                LocalDateTime time = null;
                if (!dateStr.isEmpty()) {
                    try {
                        time = LocalDateTime.parse(dateStr, formatter);
                    } catch (DateTimeParseException e) {
                        System.err.println("날짜 파싱 실패: " + dateStr);
                    }
                }

                boolean isChecked = Boolean.parseBoolean(dataFormatter.formatCellValue(row.getCell(4)));
                String answeredInquiries = dataFormatter.formatCellValue(row.getCell(5));
                boolean isPriority = Boolean.parseBoolean(dataFormatter.formatCellValue(row.getCell(6)));

                inquiries.add(new Inquiry(name, id, message, time, isChecked, answeredInquiries, isPriority));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inquiries;
    }

    // 처리/ 미처리 분류 반환
    public Map < String, List < Inquiry >> getProcessedAndUnprocessedInquiries() {
        List < Inquiry > all = loadAllInquiries();
        List < Inquiry > processed = new ArrayList < > ();
        List < Inquiry > unprocessed = new ArrayList < > ();
        for (Inquiry inquiry: all) {
            if (inquiry.getName().equals("이름") && inquiry.getId().equals("아이디")) continue;
            if (inquiry.isChecked()) processed.add(inquiry);
            else unprocessed.add(inquiry);
        }
        Map < String, List < Inquiry >> map = new HashMap < > ();
        map.put("processed", processed);
        map.put("unprocessed", unprocessed);
        return map;
    }

    // 처리여부(checked) 업데이트
    public boolean updateCheckStatus(String inquiryId, boolean checked) {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            boolean updated = false;
            for (Row row: sheet) {
                Cell idCell = row.getCell(1);
                if (idCell != null && formatter.formatCellValue(idCell).equals(inquiryId)) {
                    Cell checkCell = row.getCell(4);
                    if (checkCell == null) checkCell = row.createCell(4);
                    checkCell.setCellValue(checked);
                    updated = true;
                    break;
                }
            }
            if (updated) {
                try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH)) {
                    workbook.write(fos);
                }
            }
            workbook.close();
            return updated;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "엑셀 파일 업데이트 오류: " + ex.getMessage());
            return false;
        }
    }

    // 답변 저장
    public boolean updateAnswer(String name, String id, String time, String answer) {
        boolean updated = false;
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (Row row: sheet) {
                String rowName = formatter.formatCellValue(row.getCell(0)); // 이름 컬럼 인덱스 확인
                String rowId = formatter.formatCellValue(row.getCell(1)); // 아이디 컬럼 인덱스 확인
                String rowTime = formatter.formatCellValue(row.getCell(3)); // 문의날짜 컬럼 인덱스 확인

                if (rowName.equals(name) && rowId.equals(id) && rowTime.equals(time)) {
                    // 답변 저장
                    Cell answerCell = row.getCell(5); // 답변 컬럼 인덱스 확인
                    if (answerCell == null) answerCell = row.createCell(5);
                    answerCell.setCellValue(answer);

                    // isChecked(처리여부) true로 변경
                    Cell checkCell = row.getCell(4); // 처리여부 컬럼 인덱스 확인
                    if (checkCell == null) checkCell = row.createCell(4);
                    checkCell.setCellValue(true);

                    updated = true;
                    break;
                }
            }
            if (updated) {
                try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH)) {
                    workbook.write(fos);
                }
            }
            workbook.close();
            return updated;
        } catch (Exception ex) {
            return false;
        }
    }
    
    // 문의하기
    public boolean addInquiry(Inquiry inquiry) {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH); Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRowNum + 1);
            row.createCell(0).setCellValue(inquiry.getName());
            row.createCell(1).setCellValue(inquiry.getId());
            row.createCell(2).setCellValue(inquiry.getMessage());
            row.createCell(3).setCellValue(inquiry.getTime().format(formatter));
            row.createCell(4).setCellValue(inquiry.isChecked());
            row.createCell(5).setCellValue(inquiry.getAnsweredInquiries());
            row.createCell(6).setCellValue(inquiry.isPriority());

            try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH)) {
                workbook.write(fos);
            }
            workbook.close();
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "문의 저장 실패: " + ex.getMessage());
            return false;
        }
    }
    
    public void setExcelFilePath(String path) {
        this.EXCEL_FILE_PATH = path;
    }
}