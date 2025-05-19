/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class RoomStatus extends Excel {
    public RoomStatus() throws IOException {
        super();
    }
    
    public void printRoomStatus(String roomID) {
        int numberOfSheets = workbook.getNumberOfSheets();
        boolean found = false;
        
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
        
        if (!found) {
            System.out.println("해당 강의실 [" + roomID + "] 을/를 찾을 수 없습니다.");
        }
    }
    
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
            
            if (existingID.equals(userID) && !(existingStatus.equals("사용 종료") || existingStatus.equals("예약 취소"))) {
                activeReservations++;
            }
            
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
    
    private String getCellValue(XSSFCell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
    
    private int getTotalSeatsForRoom(String roomID) {
        switch (roomID) {
            case "911":
            case "915":
            case "916":
            case "917":
                return 40;
            case "912":
            case "913":
            case "914":
                return 56;
            default:
                return 0;
        }
    }
}
