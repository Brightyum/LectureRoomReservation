/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package admin;

import Controller.admin.UserManagement;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import Model.StubExcel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author user
 */
public class UserManagementTest {
    private UserManagement um;
    private StubExcel stubExcel;
    private Scanner scanner;
    
    @BeforeEach
    public void setUp() throws IOException {
        System.out.println("setUp()");
        this.stubExcel = new StubExcel();
        this.scanner = new Scanner("테스트");
        this.um = new UserManagement(this.stubExcel, this.scanner);
    }

    /**
     * Test of getUserInformation method, of class UserManagement.
     */
    // 기본 생성자 테스트
    @Test
    public void testDefaultConstructor() {
        System.out.printf("%s: 기본 생성자 테스트", this.um.getClass().getSimpleName());
        assertNotNull(this.um);
        assertTrue(!this.um.getUsers().isEmpty());
    }
    
    // 매개변수를 가진 생성자 테스트
    @Test
    public void testAnotherConstructor() {
        String mockInput = "홍길동";
        this.scanner = new Scanner(mockInput);

        try {
            this.stubExcel = new StubExcel();

            this.um = new UserManagement(this.stubExcel, scanner);

            assertNotNull(um); 
            List<String> ids = um.getId(); 
            assertFalse(ids.isEmpty());
            assertEquals("id123", ids.get(0));

        } catch (IOException ex) {
            fail("IOException 발생: " + ex.getMessage());
        }
    }
    
    //사용자 추가 기능 테스트
    @Test
    public void testAddUser() {
        List<String> newUser = new ArrayList<>();
        newUser.add("김광식");
        newUser.add("id100");
        newUser.add("pw101");
        newUser.add("겜공");
        newUser.add("0");
        newUser.add("-");
        newUser.add("0");
        
        boolean result = this.um.addUser(newUser);
        assertTrue(result);
    }
    
    //사용자 삭제 기능 테스트
    @Test
    public void testRemoveUser() {
        String id = this.stubExcel.getUserInfo().get(0).get(1);
        boolean result = this.um.removeUser(id);
        
        assertTrue(result);
        assertNull(this.um.getUserDetail(id));
    }
    
    //사용자 정보 수정 기능 테스트
    @Test
    public void testSetUserInformation() {
        String id = this.stubExcel.getUserInfo().get(0).get(1);
        List<String> update = new ArrayList<>();
        update.add("박병모");
        update.add("pw999");
        update.add("인문과");
        
        this.um.setUserInformation(update, id);
        
        List<String> result = this.um.getUserDetail(id);
        assertEquals("박병모", result.get(0));
        assertEquals("pw999", result.get(2));
        assertEquals("인문과", result.get(3));
        
    }
    
    //사용자 경고 주기 기능 테스트
    @Test
    public void testSetUserWarning() {
        String id = this.stubExcel.getUserInfo().get(0).get(1);
        int beforeWarning = Integer.parseInt(this.um.getUserDetail(id).get(4));
        
        this.um.setUserWarning(id);
        
        int result = Integer.parseInt(this.um.getUserDetail(id).get(4));
        
        assertEquals(beforeWarning + 1, result);
    }
    
    //사용자 정보 보기 기능 테스트
    @Test
    public void testGetUserDetail() {
        String id = this.stubExcel.getUserInfo().get(0).get(1);
        List<String> result = this.um.getUserDetail(id);
        
        assertNotNull(result);
        assertEquals(id, result.get(1));
    }
}
