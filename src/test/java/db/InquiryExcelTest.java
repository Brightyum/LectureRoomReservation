/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import professor.Inquiry;

/**
 *
 * @author leeseungmin
 */
public class InquiryExcelTest {
    
    private String TEST_EXCEL_PATH = "test_Inquiry.xlsx";
    private InquiryExcel inquiryExcel;

    public InquiryExcelTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    void setUp() throws IOException {
        // 기존 파일 삭제
        Files.deleteIfExists(Paths.get(TEST_EXCEL_PATH));

        // 새 파일 생성 
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Inquiries");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("이름");
            headerRow.createCell(1).setCellValue("아이디");
            headerRow.createCell(2).setCellValue("문의사항");
            headerRow.createCell(3).setCellValue("날짜");
            headerRow.createCell(4).setCellValue("확인여부");
            headerRow.createCell(5).setCellValue("답변");
            headerRow.createCell(6).setCellValue("중요도");
            
            try (FileOutputStream fos = new FileOutputStream(TEST_EXCEL_PATH)) {
                workbook.write(fos);
            }
        }
        inquiryExcel = new InquiryExcel();
        inquiryExcel.setExcelFilePath(TEST_EXCEL_PATH);
    }
    
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_EXCEL_PATH));
    }

    /**
     * Test of loadAllInquiries method, of class InquiryExcel.
     */
    @Test
    public void testLoadAllInquiries() { // 테스트완료
        System.out.println("loadAllInquiries");
        List<Inquiry> result = inquiryExcel.loadAllInquiries();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Test of getProcessedAndUnprocessedInquiries method, of class InquiryExcel.
     */   
    @Test
    public void testGetProcessedAndUnprocessedInquiries() { // 테스트완료
        System.out.println("getProcessedAndUnprocessedInquiries");
        InquiryExcel instance = new InquiryExcel();       
        instance.setExcelFilePath(TEST_EXCEL_PATH);
        Map<String, List<Inquiry>> result = instance.getProcessedAndUnprocessedInquiries();
        assertNotNull(result);
        assertEquals(0, result.get("processed").size());
        assertEquals(0, result.get("unprocessed").size());
    }
    
    
    
    /**
     * Test of updateCheckStatus method, of class InquiryExcel.
     */
    @Test
    public void testUpdateCheckStatus() { // 테스트 완료
        System.out.println("updateCheckStatus");
        String inquiryId = "";
        boolean checked = false;
        InquiryExcel instance = new InquiryExcel();
        boolean expResult = false;
        boolean result = instance.updateCheckStatus(inquiryId, checked);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateAnswer method, of class InquiryExcel.
     */
    @Test
    public void testUpdateAnswer() { // 테스트 완료
        System.out.println("updateAnswer");
        String name = "";
        String id = "";
        String time = "";
        String answer = "";
        InquiryExcel instance = new InquiryExcel();
        boolean expResult = false;
        boolean result = instance.updateAnswer(name, id, time, answer);
        assertEquals(expResult, result);
    }

    /**
     * Test of addInquiry method, of class InquiryExcel.
     */
    @Test
    public void testAddInquiry() { // 테스트 완료
        System.out.println("addInquiry");
        Inquiry inquiry = null;
        InquiryExcel instance = new InquiryExcel();
        boolean expResult = false;
        boolean result = instance.addInquiry(inquiry);
        assertEquals(expResult, result);
    }
    
}
