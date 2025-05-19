/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    XSSFWorkbook workbook;
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
        this.userLen = 7;
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
    
    public void saveUserInfo(List<List<String>> userInfo) {
        System.out.println("저장을 시작하겠습니다.");
        try {
            XSSFSheet sheet = this.workbook.getSheetAt(this.userSheet);

            // 기존 시트 내용을 완전히 초기화
            int lastRowNum = sheet.getLastRowNum();
            for (int i = lastRowNum; i >= 0; i--) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            // 새로운 사용자 정보 저장
            for (int i = 0; i < userInfo.size(); i++) {
                XSSFRow row = sheet.createRow(i); // 새로 생성
                List<String> user = userInfo.get(i);

                for (int j = 0; j < user.size(); j++) {
                    XSSFCell cell = row.createCell(j);
                    cell.setCellValue(user.get(j));
                }
            }

            // 파일에 저장
            try (FileOutputStream fileOut = new FileOutputStream(this.excelPath)) {
                this.workbook.write(fileOut);
                System.out.println("사용자 정보 저장 완료");
            } catch (SecurityException se) {
                System.out.println("파일 접근 권한이 없습니다: " + se);
            }

            // 디버깅용 출력
            for (List<String> user : userInfo) {
                System.out.println(user);
            }

        } catch (IOException ioe) {
            System.out.println("엑셀 파일 저장 중 입출력 오류가 발생했습니다: " + ioe);
        }
    }
    public static void main(String args[]) throws IOException {
        Excel e = new Excel();
        e.readUser();
    }
}
