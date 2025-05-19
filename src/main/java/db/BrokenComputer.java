/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.IOException;

/**
 *
 * @author user
 */
public class BrokenComputer extends Excel {
    public BrokenComputer() throws IOException {
        super();
    }
    
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
