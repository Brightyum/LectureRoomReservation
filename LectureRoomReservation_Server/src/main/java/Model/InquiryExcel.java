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

/**
 * inquiry 정보를 엑셀 파일로 관리하는 클래스 입니다.
 *
 * @author leeseungmin
 */
public class InquiryExcel {

    private String EXCEL_FILE_PATH = "src/main/java/Model/Inquiry.xlsx";    // 문의내역을 저장하는 .xlsx 파일 경로

    /**
     * 엑셀파일에서 모든 문의내역을 읽어 inquiry 객체 리스트로 반환합니다. 날짜 데이터 타입이 다르면 "날짜 파싱 실패" 메세지를
     * 콘솔에 출력합니다.
     *
     * @return 엑셀파일에서 읽은 inquiry 객체 리스트
     */
    public List<Inquiry> loadAllInquiries() {
        List<Inquiry> inquiries = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH); Workbook workbook = new XSSFWorkbook(fis)) {

            DataFormatter dataFormatter = new DataFormatter();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
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

                String isCheckedStr = dataFormatter.formatCellValue(row.getCell(4)).toLowerCase();
                boolean isChecked = Boolean.parseBoolean(isCheckedStr);

                String answeredInquiries = dataFormatter.formatCellValue(row.getCell(5));

                String isPriorityStr = dataFormatter.formatCellValue(row.getCell(6)).toLowerCase();
                boolean isPriority = Boolean.parseBoolean(isPriorityStr);

                inquiries.add(new Inquiry(name, id, message, time, isChecked, answeredInquiries, isPriority));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inquiries;
    }

    /**
     * 엑셀 파일에서 모든 문의내역을 읽어 미처리,처리로 분류한 Map을 반환합니다.
     *
     * @return 미처리,처리로 분류된 문의 리스트가 담긴 Map
     */
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

    /**
     * 답변한 문의의 처리 상태를 변경하는 기능입니다.
     *
     * @param inquiryId 처리 상태를 변경할 아이디
     * @param checked 변경할 처리상태(T:처리됨,F:미처리)
     * @return 업데이트 성공시 true , 실패시 false
     */
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

    /**
     * 클라이언트에서 답변한 문의를 엑셀파일에 추가하는 기능입니다.
     *
     * @param name 문의한 사람 이름
     * @param id 문의한 사람 아이디
     * @param time 문의한 시간
     * @param answer 답변 내용
     * @return 추가 성공시 true , 실패시 false
     */
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

    /**
     * 사용자가 작성한 새로운 문의를 엑셀파일에 추가하는 기능입니다.
     *
     * @param name 문의한 사람 이름
     * @param id 문의한 사람 아이디
     * @param message 문의한 내용
     * @param time 문의한 시간
     * @param isPriority 중요도
     * @return 추가 성공시 true , 실패시 false
     */
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

    /**
     * inquiry 객체를 엑셀 파일에 추가하는 기능입니다.
     *
     * @param inquiry 추가할 inquiry 객체
     * @return 추가 성공시 true , 실패시 false
     */
    public boolean addInquiry(Inquiry inquiry) {
        if (inquiry == null) {
            return false;
        }
        return addInquiry(
                inquiry.getName(),
                inquiry.getId(),
                inquiry.getMessage(),
                inquiry.getTime().toString(),
                inquiry.isPriority()
        );
    }

    /**
     * 특정 사용자 ID에 해당하는 문의 내역을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 해당 사용자의 Inquiry 리스트
     */
    public List<Inquiry> getInquiriesByUserId(String userId) {
        List<Inquiry> all = loadAllInquiries();
        List<Inquiry> userInquiries = new ArrayList<>();
        for (Inquiry inquiry : all) {
            if (inquiry.getId().equals(userId)) {
                userInquiries.add(inquiry);
            }
        }
        return userInquiries;
    }

    /**
     * 엑셀 파일 경로를 바꾸는 기능입니다 . test 할때 필요함
     *
     * @param path
     */
    public void setExcelFilePath(String path) {
        this.EXCEL_FILE_PATH = path;
    }
}
