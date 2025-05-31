/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author user
 */
public class BrokenComputerTest {

    public BrokenComputerTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("=== BrokenComputer Test 시작 ===");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("=== BrokenComputer Test 종료 ===");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("--- 테스트 케이스 시작 ---");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("--- 테스트 케이스 종료 ---");
    }

    /**
     * Test of getBrokenComputerList method, of class BrokenComputer.
     */
    @Test
    public void testGetBrokenComputerList() throws IOException {
        System.out.println("getBrokenComputerList");
        BrokenComputer instance = new BrokenComputer();
        List<String> result = instance.getBrokenComputerList();

        assertNotNull(result, "리스트가 null이면 안 됩니다.");
        // 실제 데이터를 기반으로 테스트할 경우, 아래처럼 예상 ID 확인도 가능
        // assertTrue(result.contains("PC-101"));

        // 디버깅 출력
        System.out.println("고장난 컴퓨터 목록:");
        result.forEach(System.out::println);
    }

    /**
     * Test of readAndPrintBrokenComputer method, of class BrokenComputer.
     */
    @Test
    public void testReadAndPrintBrokenComputer() throws IOException {
        System.out.println("readAndPrintBrokenComputer");
        BrokenComputer instance = new BrokenComputer();
        
        // 예외 없이 실행되는지만 확인
        assertDoesNotThrow(() -> instance.readAndPrintBrokenComputer());
    }
}