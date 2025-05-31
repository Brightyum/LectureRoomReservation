/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author user
 */
public class RoomReservation extends Excel {
    
    private static final String EXCEL_PATH = "src/main/java/Model/LectureRoomReservation.xlsx";
    
    public RoomReservation() throws IOException {
        super();
    }
    
    // 예약 추가 메서드
    public boolean addReservation(String roomID, String seat, String name, String userId, String date, String time) {
        try {
            XSSFSheet sheet = workbook.getSheet(roomID);
            if (sheet == null) {
                System.out.println("강의실 [" + roomID + "]이(가) 존재하지 않습니다.");
                return false;
            }

            // 중복 확인
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (row == null) continue;

                String existingDate = getCellValue(row.getCell(4)).trim();
                String existingTime = getCellValue(row.getCell(5)).trim();
                String existingSeat = getCellValue(row.getCell(1)).trim();

                if (existingDate.equals(date) && existingTime.equals(time) && existingSeat.equals(seat)) {
                    System.out.println("이미 예약된 시간입니다. [강의실: " + roomID + " | 날짜: " + date + " | 시간: " + time + " | 좌석: " + seat + "]");
                    return false;
                }
            }

            // 새 예약 추가
            int newRowNum = sheet.getLastRowNum() + 1;
            XSSFRow newRow = sheet.createRow(newRowNum);
            newRow.createCell(0).setCellValue(newRowNum); // 예약 번호
            newRow.createCell(1).setCellValue(seat);      // 좌석 번호
            newRow.createCell(2).setCellValue(name);      // 이름
            newRow.createCell(3).setCellValue(userId);    // ID
            newRow.createCell(4).setCellValue(date);      // 날짜
            newRow.createCell(5).setCellValue(time);      // 시간

            try (FileOutputStream out = new FileOutputStream(EXCEL_PATH)) {
                workbook.write(out);
                System.out.println("[예약 완료] 강의실: " + roomID + " | 이름: " + name + " | 날짜: " + date + " | 시간: " + time);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 날짜별 예약 목록 조회
    public String getReservationList(String roomID, String date) {
        StringBuilder result = new StringBuilder();
        XSSFSheet sheet = workbook.getSheet(roomID);
        if (sheet == null) return "강의실 없음";

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;

            String rowDate = getCellValue(row.getCell(4)).trim();
            if (rowDate.equals(date)) {
                String seat = getCellValue(row.getCell(1));
                String name = getCellValue(row.getCell(2));
                String id = getCellValue(row.getCell(3));
                String time = getCellValue(row.getCell(5));
                result.append(String.format("좌석:%s | 이름:%s | ID:%s | 시간:%s\n", seat, name, id, time));
            }
        }

        if (result.isEmpty()) return "해당 날짜에 예약 없음";
        return result.toString();
    }

    // getCellValue 메서드
    private String getCellValue(XSSFCell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

}
