/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
/**
 *
 * @author user
 */
public class StubExcel extends Excel {
    
     private List<List<String>> dummyData;

    public StubExcel() throws IOException {
        this.dummyData = new ArrayList<>();
        dummyData.add(new ArrayList<>(List.of("홍길동", "id123", "pw123", "컴공", "0")));
        dummyData.add(new ArrayList<>(List.of("김철수", "id456", "pw456", "소프트웨어학과", "1")));
    }

    @Override
    public void readUser() {
        // 아무 동작도 하지 않음
    }

    @Override
    public List<List<String>> getUserInfo() {
        return dummyData;
    }

    @Override
    public void saveUserInfo(List<List<String>> userInfo) {
        System.out.println("저장됨 (모의): " + userInfo);
    }
}
