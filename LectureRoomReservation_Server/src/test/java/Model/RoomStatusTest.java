/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Model;

import java.io.IOException;
import java.util.List;
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
public class RoomStatusTest {

    public RoomStatusTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of printRoomStatus method, of class RoomStatus.
     */
    @Test
    public void testPrintRoomStatus() throws IOException {
        System.out.println("printRoomStatus");
        String roomID = "301호"; // 실제 존재하는 시트명 사용
        RoomStatus instance = new RoomStatus();
        //  예외 없이 실행되는지 확인
        assertDoesNotThrow(() -> instance.printRoomStatus(roomID));
    }

    /**
     * Test of getReservationList method, of class RoomStatus.
     */
    @Test
    public void testGetReservationList() throws IOException {
        System.out.println("getReservationList");
        String roomID = "301호";
        String date = "2025-05-31";
        RoomStatus instance = new RoomStatus();
        String result = instance.getReservationList(roomID, date);
        assertNotNull(result);
    }

    /**
     * Test of getLectureRoomSheetNames method, of class RoomStatus.
     */
    @Test
    public void testGetLectureRoomSheetNames() throws IOException {
        System.out.println("getLectureRoomSheetNames");
        RoomStatus instance = new RoomStatus();
        List<String> result = instance.getLectureRoomSheetNames();
        assertNotNull(result);
        assertTrue(result.size() > 0); // 강의실 시트가 최소 1개 이상 있는지 확인
    }

    /**
     * Test of getWorkbook method, of class RoomStatus.
     */
    @Test
    public void testGetWorkbook() throws IOException {
        System.out.println("getWorkbook");
        RoomStatus instance = new RoomStatus();
        XSSFWorkbook result = instance.getWorkbook();
        assertNotNull(result);
    }

    /**
     * Test of getReservationsByRoom method, of class RoomStatus.
     */
    @Test
    public void testGetReservationsByRoom() throws IOException {
        System.out.println("getReservationsByRoom");
        String roomID = "301호";
        RoomStatus instance = new RoomStatus();
        List<String> result = instance.getReservationsByRoom(roomID);
        assertNotNull(result);
    }

}
