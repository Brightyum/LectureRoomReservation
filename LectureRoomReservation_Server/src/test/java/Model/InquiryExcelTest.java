/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Model;

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
     * Test of getProcessedAndUnprocessedInquiries method, of class
     * InquiryExcel.
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
    public void testUpdateCheckStatus() { // 테스트완료
        System.out.println("updateCheckStatus");
        String inquiryId = "";
        boolean checked = false;
        boolean expResult = false;
        boolean result = inquiryExcel.updateCheckStatus(inquiryId, checked);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateAnswer method, of class InquiryExcel.
     */
    @Test
    public void testUpdateAnswer() { // 테스트완료
        System.out.println("updateAnswer");
        String name = "이승민";
        String id = "id1";
        String message = "test";
        String time = "2025-05-31T12:00:00";
        boolean isPriority = false;
        inquiryExcel.addInquiry(name, id, message, time, isPriority);
        String answer = "테스트 답변";
        boolean result = inquiryExcel.updateAnswer(name, id, time, answer);
        assertTrue(result);
    }

    /**
     * Test of addInquiry method, of class InquiryExcel.
     */
    @Test
    public void testAddInquiry() { // 테스트완료
        System.out.println("addInquiry");
        String name = "";
        String id = "";
        String message = "";
        String time = "";
        boolean isPriority = false;
        boolean result = inquiryExcel.addInquiry(name, id, message, time, isPriority);
        assertFalse(result);
    }

    /**
     * Test of getInquiriesByUserId method, of class InquiryExcel.
     */
    @Test
    public void testGetInquiriesByUserId() { //실패
        System.out.println("getInquiriesByUserId");
        inquiryExcel.addInquiry("이승민", "id1", "test", "2025-05-31T12:00:00", false);

        // 존재하는 아이디로 조회
        List<Inquiry> hongList = inquiryExcel.getInquiriesByUserId("id1");
        assertEquals(1, hongList.size());
        assertEquals("이승민", hongList.get(0).getName());

        // 없는 아이디로 조회
        List<Inquiry> noneList = inquiryExcel.getInquiriesByUserId("id0");
        assertTrue(noneList.isEmpty());

        // 빈 아이디로 조회
        List<Inquiry> emptyList = inquiryExcel.getInquiriesByUserId("");
        assertTrue(emptyList.isEmpty());
    }

    /**
     * Test of setExcelFilePath method, of class InquiryExcel.
     */
    @Test
    public void testSetExcelFilePath() { // 테스트완료
        System.out.println("setExcelFilePath");
        String path = "";
        InquiryExcel instance = new InquiryExcel();
        instance.setExcelFilePath(path);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
