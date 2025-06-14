/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

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
                    if (row == null) {
                        continue;
                    }

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

    // 셀의 값을 문자열로 가져오는 메서드
    private String getCellValue(XSSFCell cell) {
        if (cell == null) {
            return "";
        }

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
        for (int i = 1; i < rows; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;
            String rowDate = getCellValue(row.getCell(4));
            if (rowDate.equals(date)) {
                String seat = getCellValue(row.getCell(1)); // 좌석 번호
                String name = getCellValue(row.getCell(2)); // 예약자 이름
                String id = getCellValue(row.getCell(3));   // 예약자 ID
                String time = getCellValue(row.getCell(5)); // 예약 시간

                result.append(String.format("좌석: %s  |  이름: %s  |  ID: %s  |  시간: %s\n", seat, name, id, time));
                found = true;
            }
        }
        if (!found) {
            return "해당 날짜에 예약 없음";
        }
        return result.toString();
    }

    public List<String> getLectureRoomSheetNames() {
        List<String> roomNames = new ArrayList<>();
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 2; i <= 8 && i < numberOfSheets; i++) {
            String sheetName = workbook.getSheetName(i).trim();
            roomNames.add(sheetName);
        }
        return roomNames;
    }

    public XSSFWorkbook getWorkbook() {
        return this.workbook;
    }

    public List<String> getReservationsByRoom(String roomID) {
        List<String> reservations = new ArrayList<>();
        try {
            Sheet sheet = workbook.getSheet(roomID);
            if (sheet == null) {
                return reservations;
            }
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                StringJoiner sj = new StringJoiner("|");
                for (int i = 1; i < row.getLastCellNum(); i++) { 
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        sj.add("");
                        continue;
                    }
                    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                        double excelValue = cell.getNumericCellValue();
                        if (excelValue < 1.0) { 
                            LocalTime time = LocalTime.ofNanoOfDay((long) (excelValue * 24 * 60 * 60 * 1_000_000_000L));
                            sj.add(time.format(DateTimeFormatter.ofPattern("HH:mm")));
                        } else {
                            LocalDateTime date = cell.getLocalDateTimeCellValue();
                            sj.add(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                        }
                    } else {
                        sj.add(cell.toString());
                    }
                }
                reservations.add(sj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
