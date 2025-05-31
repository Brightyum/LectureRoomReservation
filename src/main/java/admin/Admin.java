/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author user
 */
public interface Admin {
    
    // 사용자 정보 보기
    void getUserInformation();
    
    // 사용자 정보 수정
    void setUserInformation();
    
    // 사용자 문의사항 보기
    void getUserContact();
    
    // 다른 학과 사용자의 강의실 예약 확인
    void checkAnother();
    
    // 사용자 목록 확인
    List<String> getUsers();
    
    // 사용자 경고

   // public void setUserWarning();

    //void setUserWarning();

}
