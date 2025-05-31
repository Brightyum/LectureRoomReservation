/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package user;

/**
 *
 * @author user
 */
public interface User {
    // 사용자 예약
    void getReservation();
    
    // 학사 조교에게 문의
    void callAdmin();
    
    // 사용자 사용 종료
    void setUse();
    
    // 강의실 본인 예약 현황
    void getUserSituation();
}
