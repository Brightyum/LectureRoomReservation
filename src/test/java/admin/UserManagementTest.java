/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package admin;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import db.StubExcel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author user
 */
public class UserManagementTest {
    private UserManagement um;
    private UserManagement um2;
    private StubExcel stubExcel;
    private Scanner scanner;
    
    public UserManagementTest() {
       
    }
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("setUp()");
        this.um = new UserManagement();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getUserInformation method, of class UserManagement.
     */
    // 기본 생성자 테스트
    @Test
    public void testDefaultConstructor() {
        System.out.printf("%s: 기본 생성자 테스트", this.um.getClass().getSimpleName());
    }
    
    @Test
    public void testAnotherConstructor() {
        String mockInput = "홍길동";
        this.scanner = new Scanner(mockInput);
        
        try {
            this.stubExcel = new StubExcel();
            this.um2 = new UserManagement(this.stubExcel, scanner);
            System.out.printf("%s: 매개변수를 가진 생성자 테스트", this.um2.getClass().getSimpleName());
        } catch (IOException ex) {
            System.out.println("예외 발생" + ex);
        }
        
    }
    
    @Test
    public void testSetUserWarning() {
        String mockInput = "홍길동\ny\n";
        this.scanner = new Scanner(mockInput);
        
        try {
            this.stubExcel = new StubExcel();
            this.um2 = new UserManagement(this.stubExcel, scanner);
            
            System.out.println("경고 메서드 호출");
            um2.setUserWarning(); // 홍길동의 경고 횟수가 0 → 1로 바뀌어야 함

            List<List<String>> updated = stubExcel.getUserInfo();
            
            // 결과 값 비교: 예상 결과 값 vs 실제 저장된 값
            assertEquals("1", updated.get(0).get(4), "경고 횟수가 1로 증가했는지 확인");
        } catch (IOException ex) {
            System.out.println("예외 발생" + ex);
        }
        
        
    }

}
