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
public class BrokenComputer extends Excel {
    public BrokenComputer() throws IOException {
        super();
    }
    // BrokenComputer 시트에서 고장난 컴퓨터의 ID 읽어 리스트로 반환
    public List<String> getBrokenComputerList() {
        List<String> result = new ArrayList(); // 결과 저장할 리스트
        try {
            XSSFSheet sheet = this.workbook.getSheet("BrokenComputer");
            if (sheet == null) return result; // 시트가 없으면 빈 리스트
            
            int rows = sheet.getPhysicalNumberOfRows(); // 전체 행 수
            // 1번 행부터 (헤더 제외)
            for (int i = 1; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    XSSFCell cell = row.getCell(0); // 1번째 셀
                    if (cell != null) {
                        String id = cell.getStringCellValue().trim(); // 공백 제거
                        if (!id.isEmpty()) {
                            result.add(id); // 유효한 ID만 리스트에 추가
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
        }
        return result;
    }
    
    // RoomStatus에서 고장난 컴퓨터 ID 콘솔에 출력
    public void readAndPrintBrokenComputer() {
        try {
            XSSFSheet sheet = this.workbook.getSheet("BrokenComputer");
            
            if (sheet == null) {
                System.out.println("시트 'BrokenComputer'을/를 찾을 수 없습니다.");
                return;
            }
            
            int rows = sheet.getPhysicalNumberOfRows();
            
            for (int i = 1; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);
                
                if (row != null) {
                    XSSFCell computerIdCell = row.getCell(0);
                    
                    if (computerIdCell != null) {
                        String computerId = computerIdCell.getStringCellValue().trim();
                        
                        if (!computerId.isEmpty()) {
                            System.out.println("컴퓨터 ID : " + computerId);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("'BrokenComputer'을/를 읽는 중 오류가 발생했습니다. : " + e.getMessage());
        }
    }
}
