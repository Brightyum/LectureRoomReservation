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
import Model.Inquiry;

public class InquiryExcel {

    private String EXCEL_FILE_PATH = "src/main/java/Model/Inquiry.xlsx";

    public InquiryExcel() {
    }

    public List<Inquiry> loadAllInquiries() {
        List<Inquiry> inquiries = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH); Workbook workbook = new XSSFWorkbook(fis)) {

            DataFormatter dataFormatter = new DataFormatter();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"); // 패턴 복원
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    System.out.println("Row " + i + " 건너뜀 (null)");
                    continue;
                }

                String name = dataFormatter.formatCellValue(row.getCell(0));
                System.out.println("name: " + name);

                String id = dataFormatter.formatCellValue(row.getCell(1));
                System.out.println("id: " + id);

                String message = dataFormatter.formatCellValue(row.getCell(2));
                System.out.println("message: " + message);

                String dateStr = dataFormatter.formatCellValue(row.getCell(3));
                System.out.println("dateStr: " + dateStr);

                LocalDateTime time = null;
                if (!dateStr.isEmpty()) {
                    try {
                        time = LocalDateTime.parse(dateStr, formatter);
                        System.out.println("time 파싱 성공: " + time);
                    } catch (DateTimeParseException e) {
                        System.err.println("날짜 파싱 실패: " + dateStr);
                    }
                }

                String isCheckedStr = dataFormatter.formatCellValue(row.getCell(4)).toLowerCase();
                boolean isChecked = Boolean.parseBoolean(isCheckedStr);
                System.out.println("isChecked: " + isChecked);

                String answeredInquiries = dataFormatter.formatCellValue(row.getCell(5));
                System.out.println("answeredInquiries: " + answeredInquiries);

                String isPriorityStr = dataFormatter.formatCellValue(row.getCell(6)).toLowerCase();
                boolean isPriority = Boolean.parseBoolean(isPriorityStr);
                System.out.println("isPriority: " + isPriority);

                inquiries.add(new Inquiry(name, id, message, time, isChecked, answeredInquiries, isPriority));
                System.out.println("Row " + i + " 처리 완료\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inquiries;
    }

    public Map< String, List< Inquiry>> getProcessedAndUnprocessedInquiries() {
        List< Inquiry> all = loadAllInquiries();
        List< Inquiry> processed = new ArrayList<>();
        List< Inquiry> unprocessed = new ArrayList<>();
        for (Inquiry inquiry : all) {
            if (inquiry.getName().equals("이름") && inquiry.getId().equals("아이디")) {
                continue;
            }
            if (inquiry.isChecked()) {
                processed.add(inquiry);
            } else {
                unprocessed.add(inquiry);
            }
        }
        Map< String, List< Inquiry>> map = new HashMap<>();
        map.put("processed", processed);
        map.put("unprocessed", unprocessed);
        return map;
    }

    public boolean updateCheckStatus(String inquiryId, boolean checked) {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            boolean updated = false;
            for (Row row : sheet) {
                Cell idCell = row.getCell(1);
                if (idCell != null && formatter.formatCellValue(idCell).equals(inquiryId)) {
                    Cell checkCell = row.getCell(4);
                    if (checkCell == null) {
                        checkCell = row.createCell(4);
                    }
                    checkCell.setCellValue(checked);
                    updated = true;
                    break;
                }
            }
            if (updated) {
                try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH)) {
                    workbook.write(fos);
                } catch (IOException e) {
                    System.err.println("파일 저장 실패: " + e.getMessage());
                    return false;
                } catch (Exception ex) {
                    System.err.println("답변 업데이트 실패: " + ex.getMessage());
                    return false;
                }
            }
            workbook.close();
            return updated;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "엑셀 파일 업데이트 오류: " + ex.getMessage());
            return false;
        }
    }

    public boolean updateAnswer(String name, String id, String time, String answer) {
        boolean updated = false;
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                String rowName = formatter.formatCellValue(row.getCell(0));
                String rowId = formatter.formatCellValue(row.getCell(1));
                String rowTime = formatter.formatCellValue(row.getCell(3));

                if (rowName.equals(name) && rowId.equals(id) && rowTime.equals(time)) {
                    Cell answerCell = row.getCell(5);
                    if (answerCell == null) {
                        answerCell = row.createCell(5);
                    }
                    answerCell.setCellValue(answer);

                    Cell checkCell = row.getCell(4);
                    if (checkCell == null) {
                        checkCell = row.createCell(4);
                    }
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

    public boolean addInquiry(String name, String id, String message, String time, boolean isPriority) {
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            newRow.createCell(0).setCellValue(name);
            newRow.createCell(1).setCellValue(id);
            newRow.createCell(2).setCellValue(message);
            newRow.createCell(3).setCellValue(time);
            newRow.createCell(4).setCellValue(false);
            newRow.createCell(5).setCellValue("");
            newRow.createCell(6).setCellValue(isPriority);

            try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH)) {
                workbook.write(fos);
            }
            workbook.close();
            return true;
        } catch (Exception e) {
            System.err.println("[서버] addInquiry 예외: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean addInquiry(Inquiry inquiry) {
        if (inquiry == null) return false;
        return addInquiry(
                inquiry.getName(),
                inquiry.getId(),
                inquiry.getMessage(),
                inquiry.getTime().toString(),
                inquiry.isPriority()
        );
    }

    public void setExcelFilePath(String path) {
        this.EXCEL_FILE_PATH = path;
    }
}
