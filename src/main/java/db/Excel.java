/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
/**
 *
 * @author user
 */
public class Excel {
    private FileInputStream file;
    private XSSFWorkbook workbook;
    private String excelPath;
    private int userSheet;
    private int defaultExcelRowIndex;
    private int defaultExcelColumnIndex;
    
    public Excel() throws IOException {
        this.excelPath = "src/main/java/db/LectureRoomReservation.xlsx";
        this.userSheet = 0;
        this.defaultExcelRowIndex = 0;
        this.defaultExcelColumnIndex = 0;
        this.file = new FileInputStream(this.excelPath);
        workbook = new XSSFWorkbook(this.file);
    }
    
    public FileInputStream getFile()  throws IOException {
        return this.file;
    }
    
    public XSSFWorkbook getWorkbook() {
        return this.workbook;
    }
    
    public void readUser() {
        XSSFSheet userSheet = this.workbook.getSheetAt(this.userSheet);
        
        int rows = userSheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rows; i++) {
            XSSFRow row = userSheet.getRow(i);
            //System.out.println((i + 1) + "번째 시트에서 읽어온 내용");
            int cells = row.getPhysicalNumberOfCells();
            for (int j = 0; j < cells; j++) {
                XSSFCell cell = row.getCell(j);
                
                switch (cell.getCellType()) {
                    case STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        break;
                    case FORMULA:
                        // 수식 자체를 출력할 수 있으며, 필요시 evaluate하여 결과를 출력할 수도 있습니다.
                        System.out.print(cell.getCellFormula() + "\t");
                        break;
                    case BLANK:
                        System.out.print(" " + "\t");
                        break;
                    default:
                        System.out.print(" " + "\t");
                        break;
                }    
            }
            System.out.println();
        }
    }
    public static void main(String args[]) throws IOException {
        Excel e = new Excel();
        e.readUser();
    }
}
