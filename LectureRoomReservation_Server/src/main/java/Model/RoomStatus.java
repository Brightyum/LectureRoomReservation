/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.DataFormatter;

/**
 *
 * @author user
 */
public class RoomStatus extends Excel {
    public RoomStatus() throws IOException {
        super();
    }
    
    // 특정 강의실의 예약 현황을 콘솔에 출력
    public void printRoomStatus(String roomID) {
        int numberOfSheets = workbook.getNumberOfSheets();
        boolean found = false;
        
        // 모든 시트를 확인하여 해당 강의실 시트 탐색
        for (int sheetIndex = 2; sheetIndex < numberOfSheets; sheetIndex++) {
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            String sheetName = sheet.getSheetName().trim();
            
            if (sheetName.equalsIgnoreCase(roomID.trim())) {
                found = true;
                System.out.println("[" + sheetName + "] 강의실 예약 현황: ");
                int rows = sheet.getPhysicalNumberOfRows();
                
                if (rows == 0) {
                    System.out.println(" 예약 없음");
                    return;
                }
                
                // 각 행의 예약 정보를 출력
                for (int i = 1; i < rows; i++) {
                    XSSFRow row = sheet.getRow(i);
                    if (row == null) continue;
                    
                    List<String> seatInfo = new ArrayList<>();
                    for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell == null) {
                            seatInfo.add("");
                            continue;
                        }
                        
                        // 셀 타입에 따라 문자열로 변환
                        switch (cell.getCellType()) {
                            case STRING:
                                seatInfo.add(cell.getStringCellValue());
                                break;
                            case NUMERIC:
                                seatInfo.add(String.valueOf((int) cell.getNumericCellValue()));
                                break;
                            default:
                                seatInfo.add("");
                                break;
                        }
                    }
                    System.out.println(" " + String.join(" | ", seatInfo));
                }
                return;
            }
        }
        
        // 해당 강의실 시트를 찾지 못한 경우
        if (!found) {
            System.out.println("해당 강의실 [" + roomID + "] 을/를 찾을 수 없습니다.");
        }
    }
    
    // 좌석 예약 (1인당 2자리 제한) -> 엑셀에 저장
    public void reserveSeat(int seatNum, String roomID, String userName, String userID, String date, String time) {
        int totalSeats = getTotalSeatsForRoom(roomID);
        
        if (totalSeats == 0) {
            System.out.println("존재하지 않는 강의실입니다.");
            return;
        }
        
        if (seatNum < 1 || seatNum > totalSeats) {
            System.out.println("잘못된 좌석 번호입니다. 유효한 좌석 번호를 입력하세요.");
            return;
        }
        
        XSSFSheet sheet = workbook.getSheet(roomID);
        
        if (sheet == null) {
            System.out.println("시트를 찾을 수 없습니다.");
            return;
        }
        
        int rows = sheet.getPhysicalNumberOfRows();
        int activeReservations = 0;
        boolean duplicateFound = false;
        
        // 동일 시간, 좌석 중복 확인 및 중복 예약 검사
        for (int i = 1; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;
            
            String existingID = getCellValue(row.getCell(3));
            String existingStatus = getCellValue(row.getCell(6));
            String existingDate = getCellValue(row.getCell(4));
            String existingTime = getCellValue(row.getCell(5));
            String seatCell = getCellValue(row.getCell(1));
            int existingSeatNum;
            
            try {
                existingSeatNum = Integer.parseInt(seatCell);
            } catch (NumberFormatException e) {
                continue;
            }
            
            // 같은 ID로 아직 사용 종료되지 안은 예약 2개 이상
            if (existingID.equals(userID) && !(existingStatus.equals("사용 종료") || existingStatus.equals("예약 취소"))) {
                activeReservations++;
            }
            
            // 같은 시간 같은 좌석 -> 중복으로 판단
            if (existingDate.equals(date) && existingTime.equals(time) && existingSeatNum == seatNum) {
                duplicateFound = true;
            }
        }
        
        if (activeReservations >= 2) {
            System.out.println("이미 2건 이상의 예약이 된 사용자는 예약할 수 없습니다.");
            return;
        }
        
        if (duplicateFound) {
            System.out.println("해당 좌석은 이미 예약이 존재합니다.");
            return;
        }
        // 예약 정보 추가
        int reserveNo = rows;
        
        XSSFRow newRow = sheet.createRow(rows);
        newRow.createCell(0).setCellValue(reserveNo);
        newRow.createCell(1).setCellValue(seatNum);
        newRow.createCell(2).setCellValue(userName);
        newRow.createCell(3).setCellValue(userID);
        newRow.createCell(4).setCellValue(date);
        newRow.createCell(5).setCellValue(time);
        newRow.createCell(6).setCellValue("예약됨");
        
        saveUserInfo(getUserInfo());
        System.out.println("예약이 완료되었습니다.");
    }
    
    // 셀의 값을 문자열로 가져오는 메서드
    private String getCellValue(XSSFCell cell) {
        if (cell == null) return "";
        
        DataFormatter formatter = new DataFormatter();
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return formatter.formatCellValue(cell);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
    
    // 특정 강의실, 특정 날짜에 해당하는 예약 목록 문자열로 반환
    public String getReservationList(String roomID, String date) {
        StringBuilder result = new StringBuilder();
        
        XSSFSheet sheet = workbook.getSheet(roomID);
        if (sheet == null) {
            return "없는 강의실";
        }
        
        int rows = sheet.getPhysicalNumberOfRows();
        
        boolean found = false;
        
        // 해당 날짜의 예약 정보만 필터링
        for (int i = 1; i < rows; i ++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;
            
            String rowDate = getCellValue(row.getCell(4));
            if (rowDate.equals(date)) {
                String seat = getCellValue(row.getCell(1));
                String name = getCellValue(row.getCell(2));
                String id = getCellValue(row.getCell(3));
                String time = getCellValue(row.getCell(5));
                
                result.append(String.format("좌석: %s  |  이름: %s  |  ID: %s  |  시간: %s\n", seat, name, id, time));
                found = true;
            }
        }
        
        if (!found) {
            return "해당 날짜에 예약 없음";
        }
        
        return result.toString();
    }
    
    // 강의실에 따른 좌석 수
    private int getTotalSeatsForRoom(String roomID) {
        switch (roomID) {
            case "911":
            case "915":
            case "916":
            case "917":
                return 40; // 실습실
            case "912":
            case "913":
            case "914":
                return 56; // 강의실
            default:
                return 0; // 잘못된 강의실
        }
    }
}
