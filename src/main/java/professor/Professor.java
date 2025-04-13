/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package professor;

/**
 *
 * @author user
 */
public interface Professor {
    // 강의실 예약은 사용자와 동일하게 진행
    
    // 문의사항 전체 확인(학사조교 - 사용자)
    void getContactAll();
    
    // 강의실 사용 명단 확인
    void getRoster();
    
    // 강의실 과거 사용 명단 확인
    void getPastRoster();
}
