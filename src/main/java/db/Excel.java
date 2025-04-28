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
import java.util.List;
import java.util.ArrayList;

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
    private List<String> userList;
    private int userLen;
    
    public Excel() throws IOException {
        this.excelPath = "src/main/java/db/LectureRoomReservation.xlsx";
        this.userSheet = 0;
        this.defaultExcelRowIndex = 0;
        this.defaultExcelColumnIndex = 0;
        this.file = new FileInputStream(this.excelPath);
        this.workbook = new XSSFWorkbook(this.file);
        this.userList = new ArrayList<>();
        this.userLen = 5;
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
                        //System.out.print("문자열" + cell.getStringCellValue() + "\t");
                        this.userList.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        //System.out.print("숫자" + cell.getNumericCellValue() + "\t");
                        String value = Integer.toString((int) cell.getNumericCellValue());
                        this.userList.add(value);
                        break;
                    case BLANK:
                        System.out.print(" " + "\t");
                        break;
                    default:
                        System.out.print("없는값 " + "\t");
                        break;
                }    
            }
            System.out.println();
        }
    }
    public List<List<String>> getUserInfo() {
        List<List<String>> result = new ArrayList<>();
        
        for (int i = 0; i < this.userList.size(); i += this.userLen) {
            int end = Math.min(i + userLen, userList.size());
            List<String> temp = new ArrayList<>(userList.subList(i, end));
            
            result.add(temp);
        }
        return result;
    }
    public static void main(String args[]) throws IOException {
        Excel e = new Excel();
        e.readUser();
    }
}
