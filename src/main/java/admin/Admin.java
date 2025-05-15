/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
/**
 *
 * @author user
 */
/*
    사용자 정보를 어디에 저장할것인가.
    사용자 정보를 저장하면 그것을 읽어와서 보여주기

    */
public interface Admin {
    
    // 사용자 정보 보기
    //void getUserInformation();
    
    // 사용자 정보 수정
   // public void setUserInformation();
    
    // 사용자 문의사항 보기
    public void getUserContact();
    
    // 다른 학과 사용자의 강의실 예약 확인
    public void checkAnother();
    
    // 사용자 목록 확인
    public List<String> getUsers();
    
    // 사용자 경고
    public void setUserWarning();
}
